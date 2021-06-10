package com.example.vote.service;

import com.example.vote.domain.Pauta;
import com.example.vote.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PautaService {

    @Autowired
    private PautaRepository pautaRepository;

    public Pauta save(Pauta pauta){
        return pautaRepository.save(pauta);
    }

}
