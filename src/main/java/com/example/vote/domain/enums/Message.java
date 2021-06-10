package com.example.vote.domain.enums;


import com.example.vote.domain.exception.BusinessException;
import org.springframework.http.HttpStatus;

public enum Message {

    PAUTA_NOT_FOUND("Pauta nao encontrada!", HttpStatus.BAD_REQUEST),
    PAUTA_WAS_OPEN("Pauta ja foi aberta!", HttpStatus.BAD_REQUEST),
    VOTING_CLOSED("Votacao encerrada para esta pauta!", HttpStatus.BAD_REQUEST),
    PAUTA_IS_CLOSED("Pauta esta fechada!", HttpStatus.BAD_REQUEST),
    PAUTA_IS_OPEN("Pauta ainda aberta para votação!", HttpStatus.BAD_REQUEST),
    ASSOCIADO_VOTED("Associado ja votou!", HttpStatus.BAD_REQUEST),
    INVALID_ID("Id invalido para associado!", HttpStatus.BAD_REQUEST);

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
