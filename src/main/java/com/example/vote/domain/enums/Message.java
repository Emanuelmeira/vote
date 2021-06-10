package com.example.vote.domain.enums;


import com.example.vote.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public enum Message {

    PAUTA_NOT_FOUND("Pauta nao encontrada!", HttpStatus.BAD_REQUEST),
    PAUTA_WAS_OPEN("Pauta ja foi aberta!", HttpStatus.BAD_REQUEST);

    private String value;

    private HttpStatus statusCode;

    Message(String value, HttpStatus statusCode) {
        this.value = value;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return this.value;
    }
    public HttpStatus getStatus() {
        return this.statusCode;
    }

    public BusinessException asBusinessException() {

        BusinessException businessException = new BusinessException();
        businessException.setMessage(this.getMessage());
        businessException.setHttpStatusCode(this.getStatus());
        return businessException;
    }


}
