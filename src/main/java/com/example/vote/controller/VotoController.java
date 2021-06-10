package com.example.vote.controller;

import com.example.vote.domain.dto.DataToVote;

import com.example.vote.service.VotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
    Logger logger = LoggerFactory.getLogger(VotoController.class);

    @Autowired
    private VotoService votoService;

    @PostMapping(value = "/{associadoId}/{pautaId}")
    public ResponseEntity<Object> vote(@Valid @RequestBody DataToVote dataToVoto,
                                       @PathVariable("associadoId") Long associadoId,
                                       @PathVariable("pautaId") Long pautaId){
        logger.info("Processo para computar voto iniciado");
        votoService.vote(dataToVoto, associadoId, pautaId);
        return ResponseEntity.noContent().build();
    }

}
