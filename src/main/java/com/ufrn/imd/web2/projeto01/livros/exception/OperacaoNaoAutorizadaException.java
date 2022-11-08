package com.ufrn.imd.web2.projeto01.livros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class OperacaoNaoAutorizadaException extends RuntimeException{

    public OperacaoNaoAutorizadaException(String message) {
        super(message);
    }
    
}
