package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.Voto;
import com.example.vote.domain.dto.DataToVote;
import com.example.vote.domain.enums.Message;
import com.example.vote.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class VotoService {

    Logger logger = LoggerFactory.getLogger(VotoService.class);

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotoRepository votoRepository;

    public void vote(DataToVote votoType, Long associadoId, Long pautaId) {

        var pauta = pautaService.checkExistingPauta(pautaId);

        sessionIsValid(pauta);
        memberCanVote(associadoId, pauta.getId());

        var voto = new Voto();
        voto.setVoto(votoType.getVotoType());
        voto.setAssociadoId(associadoId);
        voto.setPautaId(pautaId);

        votoRepository.save(voto);
        logger.info("voto computado {}", voto);
    }

    public void memberCanVote(Long associadoId, Long pautaId){

        if(Objects.isNull(associadoId)){
            logger.error("id de associado é invalido");
            throw Message.INVALID_ID.asBusinessException();
        }

        //verifica se o associado ja votou
        var voto = votoRepository.findFirstByAssociadoIdAndPautaId(associadoId, pautaId);
        if(Objects.nonNull(voto)){
            logger.error("Associado ja votou associadoId{}", associadoId );
            throw Message.ASSOCIADO_VOTED.asBusinessException();
        }

        logger.info("Associado disponivel para votação associdoId{}, pauta{}", associadoId, pautaId);
    }

    public void sessionIsValid(Pauta pauta){

        //verifica se a pauta esta aberta para votação
        if(!pauta.isOpen()){
            logger.error("Pauta esta fechada Pauta{}", pauta );
            throw Message.PAUTA_IS_CLOSED.asBusinessException();
        }

        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());

        //verifica se a sessão de votação ainda esta aberta
        if(sessionEndTme.isBefore(LocalDateTime.now())){
            logger.error("Pauta esta fechada Pauta{}", pauta );
            throw Message.VOTING_CLOSED.asBusinessException();
        }

    }





}
