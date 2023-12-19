package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> recuperarTodos() {
        return usuarioService.recuperarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> recuperarPorId(@PathVariable("id") String id) {
        return usuarioService.recuperarPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable("id") Long id) {
        return usuarioService.desativarUsuario(id);
    }
}
