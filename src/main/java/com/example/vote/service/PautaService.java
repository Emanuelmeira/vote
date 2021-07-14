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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    Logger logger = LoggerFactory.getLogger(PautaService.class);

    private final  PautaRepository pautaRepository;
    private final VotoRepository votoRepository;

    public PautaService(final PautaRepository pautaRepository, final VotoRepository votoRepository) {
        this.pautaRepository = pautaRepository;
        this.votoRepository = votoRepository;
    }

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

        var pauta = getPautaById(pautaId);
        ifPautaIsOpenThrowException(pauta);

        var sessionEndTime = Optional.ofNullable(openSessionTimeDTO.getTimeInMinutes())
                .orElse(DefaultValues.PAUTA_VALUE_TIME_DEFAULT);
        pauta.setEndsIn(sessionEndTime);
        pauta.setOpen(true);
        pauta.setSessionStartedTime(LocalDateTime.now());

        logger.info("Sessão aberta para pauta {}", pauta );
        return pautaRepository.save(pauta);
    }

    public Pauta getPautaById(Long pautaId){
        return pautaRepository.findById(pautaId).orElseThrow(Message.PAUTA_NOT_FOUND::asBusinessException);
    }

    public VotingResultDTO generateVotingResultForPauta(Long pautaId) {

        var pauta = getPautaById(pautaId);
        ifPautaIsClosedThrowException(pauta);
        ifSessionTimeIsOpenThrowException(pauta);

        var votes = votoRepository.findByPautaId(pautaId);
        long numberOfVotesYes = votes.stream()
                .filter( vote -> vote.getVotoType().equals(VotoType.SIM))
                .count();
        long numberOfVotesNo = votes.size() - numberOfVotesYes;

        VotingResultDTO votingResultDTO = new VotingResultDTO();
        var voteResult  = numberOfVotesYes > numberOfVotesNo ? DefaultValues.APPROVED : DefaultValues.NOT_APPROVED;
        votingResultDTO.setResult(voteResult);

        logger.info("Resultado gerado para pauta {}", pauta );
        return votingResultDTO;
    }

    public void ifSessionTimeIsOpenThrowException(Pauta pauta) {
        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());
        if(!sessionEndTme.isBefore(LocalDateTime.now())){
            logger.error("Sessão ainda esta aberta para votacao");
            throw Message.PAUTA_IS_OPEN.asBusinessException();
        }
    }

    public void ifPautaIsClosedThrowException(Pauta pauta) {
        if(!pauta.isOpen()) {
            logger.error("Pauta esta fechada");
            throw Message.PAUTA_IS_CLOSED.asBusinessException();
        }
    }

    public void ifPautaIsOpenThrowException(Pauta pauta) {
        if(pauta.isOpen()){
            logger.error("sessao já esta aberta");
            throw Message.PAUTA_WAS_OPEN.asBusinessException();
        }
    }
}
