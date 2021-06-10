package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.DataToOpenSession;
import com.example.vote.domain.enums.DefaultValues;
import com.example.vote.domain.enums.Message;
import com.example.vote.domain.enums.VotoType;
import com.example.vote.repository.PautaRepository;
import com.example.vote.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private VotoRepository votoRepository;

    public Pauta save(Pauta pauta){
        pauta.setOpen(false);
        pauta.setEndsIn(0L);
        pauta.setSessionStartedTime(null);
        return pautaRepository.save(pauta);
    }

    public Pauta openSession(Long pautaId, DataToOpenSession dataToOpenSession) {

        var pauta = checkExistingPauta(pautaId);

        if(pauta.isOpen()){
           throw Message.PAUTA_WAS_OPEN.asBusinessException();
        }

        pauta.setOpen(true);
        pauta.setEndsIn(Optional.ofNullable(dataToOpenSession.getTimeInMinutes()).orElse(DefaultValues.PAUTA_VALUE_TIME_DEFAULT));
        pauta.setSessionStartedTime(LocalDateTime.now());
        return pautaRepository.save(pauta);
    }

    public Pauta checkExistingPauta(Long pautaId){
        return pautaRepository.findById(pautaId).orElseThrow(Message.PAUTA_NOT_FOUND::asBusinessException);
    }

    public String result(Long pautaId) {

        var pauta = checkExistingPauta(pautaId);
        String result;

        if(!pauta.isOpen()) {
            throw Message.PAUTA_IS_CLOSED.asBusinessException();
        }

        //verifica se a sessão de votação ainda esta aberta
        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());
        if(!sessionEndTme.isBefore(LocalDateTime.now())){
            throw Message.PAUTA_IS_OPEN.asBusinessException();
        }

        //gerar resultado da pauta
        var votos = votoRepository.findByPautaId(pautaId);
        var yes = votos.stream().filter( x -> x.getVoto().equals(VotoType.SIM)).count();
        var no = votos.stream().filter( x -> x.getVoto().equals(VotoType.NAO)).count();

        if(yes > no) {
            result = DefaultValues.APPROVED;
        }else{
            result = DefaultValues.NOT_APPROVED;
        }

        return result;
    }
}
