package com.example.vote.domain.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusinessException extends RuntimeException {

    @JsonIgnore
    private HttpStatus httpStatusCode;

    private String code;

    private String message;


    public BusinessExceptionBody getOnlyBody() {
        BusinessExceptionBody body =  new BusinessExceptionBody();
        body.setCode(this.code);
        body.setMessage(this.message);
        return body;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BusinessExceptionBody {

        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
