package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.Voto;
import com.example.vote.domain.dto.DataToVote;
import com.example.vote.domain.enums.Message;
import com.example.vote.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class VotoService {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotoRepository votoRepository;

    public void vote(DataToVote votoType, Long associadoId, Long pautaId) {

        //verificar se pauta existe
        var pauta = pautaService.checkExistingPauta(pautaId);

        //verificar se sessão esta aberta E valida para votação
        sessionIsValid(pauta);

        //verificar se associado pode votar
        memberCanVote(associadoId, pauta.getId());

        //computando voto
        var voto = new Voto();
        voto.setVoto(votoType.getVotoType());
        voto.setAssociadoId(associadoId);
        voto.setPautaId(pautaId);

        votoRepository.save(voto);
    }

    public void memberCanVote(Long associadoId, Long pautaId){

        if(Objects.isNull(associadoId)){
            throw Message.INVALID_ID.asBusinessException();
        }

        //verifica se o associado ja votou
        var voto = votoRepository.findFirstByAssociadoIdAndPautaId(associadoId, pautaId);
        if(Objects.nonNull(voto)){
            throw Message.ASSOCIADO_VOTED.asBusinessException();
        }

    }

    public void sessionIsValid(Pauta pauta){

        //verifica se a pauta esta aberta para votação
        if(!pauta.isOpen()){
            throw Message.PAUTA_IS_CLOSED.asBusinessException();
        }

        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());

        //verifica se a sessão de votação ainda esta aberta
        if(sessionEndTme.isBefore(LocalDateTime.now())){
            throw Message.VOTING_CLOSED.asBusinessException();
        }

    }





}
