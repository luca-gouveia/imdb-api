package com.gouveia.imdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class LoginErrorException extends RuntimeException {
    public LoginErrorException(String mensagem) {
        super(mensagem);
    }
}
