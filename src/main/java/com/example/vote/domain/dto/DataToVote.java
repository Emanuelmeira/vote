package com.example.vote.domain.dto;

import com.example.vote.domain.enums.VotoType;
import javax.validation.constraints.NotNull;

public class DataToVote {

    @NotNull
    private VotoType votoType;

    public VotoType getVotoType() {
        return votoType;
    }

    public void setVotoType(VotoType votoType) {
        this.votoType = votoType;
    }

    public DataToVote(VotoType votoType) {
        this.votoType = votoType;
    }

    public DataToVote() {

    }
}
