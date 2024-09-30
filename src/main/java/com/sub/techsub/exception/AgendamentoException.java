package com.sub.techsub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AgendamentoException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;
    public AgendamentoException(String message, Exception e) {super(message, e);}
    public AgendamentoException(String message) {super(message);}
}
