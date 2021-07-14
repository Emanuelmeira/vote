package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.Voto;
import com.example.vote.domain.dto.DataToVoteDTO;
import com.example.vote.domain.enums.Message;
import com.example.vote.repository.VotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class VotoService {

    Logger logger = LoggerFactory.getLogger(VotoService.class);

    private final PautaService pautaService;
    private final VotoRepository votoRepository;

    public VotoService(final PautaService pautaService, final VotoRepository votoRepository) {
        this.pautaService = pautaService;
        this.votoRepository = votoRepository;
    }

    public void vote(DataToVoteDTO votoType, Long associadoId, Long pautaId) {

        var pauta = pautaService.getPautaById(pautaId);

        sessionIsValid(pauta);
        memberCanVote(associadoId, pauta.getId());

        var voto = new Voto();
        voto.setVotoType(votoType.getVotoType());
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

        ifAssociadoVotedThrowExeption(associadoId, pautaId);

        logger.info("Associado disponivel para votação associdoId{}, pauta{}", associadoId, pautaId);
    }

    private void ifAssociadoVotedThrowExeption(Long associadoId, Long pautaId) {
        var voto = votoRepository.findFirstByAssociadoIdAndPautaId(associadoId, pautaId);
        if(Objects.nonNull(voto)){
            logger.error("Associado ja votou associadoId{}", associadoId);
            throw Message.ASSOCIADO_VOTED.asBusinessException();
        }
    }

    public void sessionIsValid(Pauta pauta){
        pautaService.ifPautaIsClosedThrowException(pauta);
        ifPautaIsClosedThrowException(pauta);
    }

    private void ifPautaIsClosedThrowException(Pauta pauta) {
        var sessionEndTme = pauta.getSessionStartedTime().plusMinutes(pauta.getEndsIn());
        if(sessionEndTme.isBefore(LocalDateTime.now())){
            logger.error("Pauta esta fechada Pauta{}", pauta);
            throw Message.VOTING_CLOSED.asBusinessException();
        }
    }


}
