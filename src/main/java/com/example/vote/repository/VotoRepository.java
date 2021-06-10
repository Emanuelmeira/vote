package com.example.vote.repository;

import com.example.vote.domain.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {

    Voto findFirstByAssociadoIdAndPautaId(Long associadoId, Long pautaId);
    List<Voto> findByPautaId(Long pautaId);
}
