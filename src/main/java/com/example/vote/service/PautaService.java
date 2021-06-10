package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.DataToOpenSession;
import com.example.vote.domain.enums.DefaultValues;
import com.example.vote.domain.enums.Message;
import com.example.vote.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta save(Pauta pauta){
        pauta.setOpen(false);
        pauta.setEndsIn(0L);
        pauta.setSessionStartedTime(null);
        return pautaRepository.save(pauta);
    }

    public void openSession(Long pautaId, DataToOpenSession dataToOpenSession) {

        var pauta = checkExistingPauta(pautaId);

        if(pauta.isOpen()){
           throw Message.PAUTA_WAS_OPEN.asBusinessException();
        }

        pauta.setOpen(true);
        pauta.setEndsIn(Optional.ofNullable(dataToOpenSession.getTimeInMinutes()).orElse(DefaultValues.PAUTA_VALUE_TIME_DEFAULT.getValue()));
        pautaRepository.save(pauta);
    }

    public Pauta checkExistingPauta(Long pautaId){
        return pautaRepository.findById(pautaId).orElseThrow(Message.PAUTA_NOT_FOUND::asBusinessException);
    }

}
