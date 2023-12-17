package com.gouveia.imdb.controller;

import com.gouveia.imdb.enums.Genero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogo")
public class CatalogoControlller {

    @GetMapping
    public ResponseEntity recuperarTodosFilmes() {
        return ResponseEntity.ok(Genero.ACAO.getDescricao());
    }
}
