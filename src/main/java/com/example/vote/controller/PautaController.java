package com.example.vote.controller;

import com.example.vote.domain.Pauta;
import com.example.vote.domain.dto.DataToOpenSession;
import com.example.vote.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<Pauta> create(@Valid @RequestBody Pauta pauta){
        return ResponseEntity.ok(pautaService.save(pauta));
    }

    @PostMapping(value = "/opensession/{pautaId}")
    public ResponseEntity<Object> openSession(@PathVariable("pautaId") Long pautaId, @Valid @RequestBody DataToOpenSession DataToOpenSession ){

        pautaService.openSession(pautaId, DataToOpenSession);

        return ResponseEntity.noContent().build();
    }

}
