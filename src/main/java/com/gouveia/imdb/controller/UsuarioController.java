package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> recuperarTodos() {
        return usuarioService.recuperarTodos();
    }

    @DeleteMapping("/{email}")
    public void deletarLivro(@PathVariable("email") String email) {
        usuarioService.desativarUsuario(email);
    }

}
