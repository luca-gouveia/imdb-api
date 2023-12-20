package com.gouveia.imdb.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictErrorException extends RuntimeException {
    public ConflictErrorException(String mensagem) {
        super(mensagem);
    }
}
