package com.example.vote.domain.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class PautaDTO {

    @NotBlank(message = "Informe um tema para a Pauta")
    @Length(max=50, min = 3, message = "Campo deve ter entre 3 e 50 caracteres")
    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
