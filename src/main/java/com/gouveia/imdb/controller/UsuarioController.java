package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.dto.UsuarioRequestDTO;
import com.gouveia.imdb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> recuperarTodos() {
        var usuarios = usuarioService.recuperarTodos();

        for(UsuarioDTO usuario : usuarios) {
            usuario.add(linkTo(methodOn(UsuarioController.class).recuperarPorId(usuario.getId())).withSelfRel());
        }

        return new ResponseEntity<List<UsuarioDTO>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> recuperarPorId(@PathVariable("id") Long id) {
        var usuario =  usuarioService.recuperarPorId(id);

        if (usuario != null) {
            usuario.add(linkTo(methodOn(UsuarioController.class).recuperarTodos()).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> desativar(@PathVariable("id") Long id) {
        var isDesativado =  usuarioService.desativarUsuario(id);

        if (isDesativado) {
            return ResponseEntity.ok().body("Usuário removido!");
        } else {
            throw new RuntimeException("Não foi possível desativar o usuário");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable("id") Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        var usuario =  usuarioService.editar(id, usuarioRequestDTO);

        if (usuario != null) {
            return ResponseEntity.ok().body(usuario);
        } else {
            throw new RuntimeException("Não foi editar o usuário");
        }
    }
}
