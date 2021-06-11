package com.example.vote.service;


import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.OpenSessionTimeDTO;
import com.example.vote.domain.dto.PautaDTO;
import com.example.vote.domain.dto.VotingResultDTO;
import com.example.vote.domain.enums.DefaultValues;
import com.example.vote.domain.enums.Message;
import com.example.vote.domain.enums.VotoType;
import com.example.vote.repository.PautaRepository;
import com.example.vote.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    Logger logger = LoggerFactory.getLogger(PautaService.class);

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    public Pauta save(PautaDTO pautaDto){

        var pauta = new Pauta();

        pauta.setTheme(pautaDto.getTheme());
        pauta.setOpen(false);
        pauta.setEndsIn(0L);
        pauta.setSessionStartedTime(null);

        pauta = pautaRepository.save(pauta);
        logger.info("Pauta salva {}" , pauta);
        return pauta;
    }

    public Pauta openSession(Long pautaId, OpenSessionTimeDTO openSessionTimeDTO) {

        var pauta = checkExistingPauta(pautaId);

        if(pauta.isOpen()){
            logger.error("sessao já esta aberta");
           throw Message.PAUTA_WAS_OPEN.asBusinessException();
        }

        pauta.setOpen(true);
        pauta.setEndsIn(Optional.ofNullable(openSessionTimeDTO.getTimeInMinutes()).orElse(DefaultValues.PAUTA_VALUE_TIME_DEFAULT));
        pauta.setSessionStartedTime(LocalDateTime.now());

        logger.info("Sessão aberta para pauta {}", pauta );
        return pautaRepository.save(pauta);
    }

    public Pauta checkExistingPauta(Long pautaId){
        return pautaRepository.findById(pautaId).orElseThrow(Message.PAUTA_NOT_FOUND::asBusinessException);
    }

    public VotingResultDTO generateResult(Long pautaId) {

        var pauta = checkExistingPauta(pautaId);
        VotingResultDTO votingResultDTO = new VotingResultDTO();

        if(!pauta.isOpen()) {
            logger.error("Pauta esta fechada");
            throw Message.PAUTA_IS_CLOSED.asBusinessException();
        }

        //verifica se a sessão de votação ainda esta aberta
        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());
        if(!sessionEndTme.isBefore(LocalDateTime.now())){
            logger.error("Sessão ainda esta aberta para votacao");
            throw Message.PAUTA_IS_OPEN.asBusinessException();
        }

        //gerar resultado da pauta
        var votos = votoRepository.findByPautaId(pautaId);
        var yes = votos.stream().filter( x -> x.getVoto().equals(VotoType.SIM)).count();
        var no = votos.stream().filter( x -> x.getVoto().equals(VotoType.NAO)).count();

        if(yes > no) {
            votingResultDTO.setResult(DefaultValues.APPROVED);
        }else{
            votingResultDTO.setResult(DefaultValues.NOT_APPROVED);
        }

        logger.info("Resultado gerado para pauta {}", pauta );
        return votingResultDTO;
    }
}
