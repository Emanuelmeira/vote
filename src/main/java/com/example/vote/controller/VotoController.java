package com.example.vote.controller;

import com.example.vote.domain.dto.DataToVoteDTO;

import com.example.vote.service.VotoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/voto")
@Api("API Voto")
public class VotoController {
    Logger logger = LoggerFactory.getLogger(VotoController.class);

    @Autowired
    private VotoService votoService;

    @PostMapping(value = "/{associadoId}/{pautaId}")
    public ResponseEntity<Object> vote(@Valid @RequestBody DataToVoteDTO dataToVoto,
                                       @PathVariable("associadoId") Long associadoId,
                                       @PathVariable("pautaId") Long pautaId){
        logger.info("Processo para computar voto iniciado");
        votoService.vote(dataToVoto, associadoId, pautaId);
        return ResponseEntity.noContent().build();
    }

}
