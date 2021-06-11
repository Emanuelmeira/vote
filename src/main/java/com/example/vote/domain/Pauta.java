package com.example.vote.domain;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PAUTA")
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return Objects.equals(id, pauta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, sessionStartedTime, endsIn, open);
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", sessionStartedTime=" + sessionStartedTime +
                ", endsIn=" + endsIn +
                ", open=" + open +
                '}';
    }
}
