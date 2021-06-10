package com.example.vote.domain;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAUTA")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe um tema para a Pauta") // TODO revisar
    @Length(max=4) // TODO revisar
    @Column(name = "theme")
    private String theme;

    @Column(name = "session_started_time")
    private LocalDateTime sessionStartedTime;

    @Column(name = "ends_in")
    private Long endsIn;

    @Column(name = "open_session")
    private boolean open;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getSessionStartedTime() {
        return sessionStartedTime;
    }

    public void setSessionStartedTime(LocalDateTime sessionStartedTime) {
        this.sessionStartedTime = sessionStartedTime;
    }

    public Long getEndsIn() {
        return endsIn;
    }

    public void setEndsIn(Long endsIn) {
        this.endsIn = endsIn;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
