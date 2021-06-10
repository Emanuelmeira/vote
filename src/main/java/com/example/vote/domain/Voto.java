package com.example.vote.domain;


import com.example.vote.domain.enums.VotoType;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "VOTO")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "associado_id")
    private Long associadoId;

    @Column(name = "pauta_id")
    private Long pautaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "voto")
    private VotoType votoType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssociadoId() {
        return associadoId;
    }

    public void setAssociadoId(Long associadoId) {
        this.associadoId = associadoId;
    }

    public Long getPautaId() {
        return pautaId;
    }

    public void setPautaId(Long pautaId) {
        this.pautaId = pautaId;
    }

    public VotoType getVoto() {
        return votoType;
    }

    public void setVoto(VotoType votoType) {
        this.votoType = votoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equals(id, voto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, associadoId, pautaId, votoType);
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id=" + id +
                ", associadoId=" + associadoId +
                ", pautaId=" + pautaId +
                ", votoType=" + votoType +
                '}';
    }
}
