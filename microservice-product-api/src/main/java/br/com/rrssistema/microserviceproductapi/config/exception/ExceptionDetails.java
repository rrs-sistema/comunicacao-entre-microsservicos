package br.com.rrssistema.microserviceproductapi.config.exception;

import lombok.Data;

@Data
public class ExceptionDetails {
    private int status;
    private String message;
}
