package com.example.vote.controller;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.DataToOpenSession;
import com.example.vote.domain.dto.VotingResult;
import com.example.vote.service.PautaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
    Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<Pauta> create(@Valid @RequestBody Pauta pauta){
        logger.info("Processo de criacao de pauta iniciado");
        return ResponseEntity.ok(pautaService.save(pauta));
    }

    @PostMapping(value = "/opensession/{pautaId}")
    public ResponseEntity<Pauta> openSession(@PathVariable("pautaId") Long pautaId, DataToOpenSession DataToOpenSession ){
        logger.info("Processo de abertura de votacao em pauta iniciado");
        return ResponseEntity.ok(pautaService.openSession(pautaId, DataToOpenSession));
    }

    @GetMapping(value = "/result/{pautaId}")
    public ResponseEntity<VotingResult> result(@PathVariable("pautaId") Long pautaId){
        logger.info("Processo de resultado de votacao iniciado");
        return ResponseEntity.ok(pautaService.generateResult(pautaId));
    }

}
