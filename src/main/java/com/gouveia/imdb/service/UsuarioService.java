package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> recuperarTodos() {
        return usuarioRepository.findAllByAtivo(true);
    }

    public ResponseEntity<String> desativarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAndAtivo(id, true);

        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            return ResponseEntity.ok().body("Usuário removido!");
        } else {
            throw new RuntimeException("Não foi possível desativar o usuário");
        }
    }

    public ResponseEntity<UsuarioDTO> recuperarPorId(String id) {
        var usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            var usuario = usuarioOptional.get();
            return ResponseEntity.ok(new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole()));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
