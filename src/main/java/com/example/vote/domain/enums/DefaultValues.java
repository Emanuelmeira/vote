package com.example.vote.domain.enums;

public enum DefaultValues {
    PAUTA_VALUE_TIME_DEFAULT(1L);

    private long value;

    DefaultValues(long time) {
        this.value = time;
    }

    public long getValue() {
        return value;
    }
}
