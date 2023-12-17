package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void desativarUsuario(String email) {
        Optional<Usuario> usuario = usuarioRepository.getByEmail(email);
        usuario.ifPresent(value -> usuarioRepository.delete(value));
    }
}
