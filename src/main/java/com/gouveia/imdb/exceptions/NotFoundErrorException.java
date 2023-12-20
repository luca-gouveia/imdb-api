package com.gouveia.imdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundErrorException extends RuntimeException {
    public NotFoundErrorException(String mensagem) {
        super(mensagem);
    }
}
