package com.gouveia.imdb.controller;

import com.gouveia.imdb.enums.Genero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @PostMapping
    public ResponseEntity recuperarTodosFilmes() {
        return ResponseEntity.ok(Genero.ACAO.getDescricao());
    }
}
