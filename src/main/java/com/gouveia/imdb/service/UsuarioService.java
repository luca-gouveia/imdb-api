package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.dto.UsuarioRequestDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UsuarioDTO> recuperarTodos() {
        var usuarios = usuarioRepository.findAllByAtivo(true);
        var usuariosDTO = new ArrayList<UsuarioDTO>();

        for (Usuario usuario : usuarios) {
            usuariosDTO.add(modelMapper.map(usuario, UsuarioDTO.class));
        }

        return usuariosDTO;
    }

    public Boolean desativarUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findByIdAndAtivo(id, true);

        var usuarioLogado = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            usuarioLogado = null;
        }

        Usuario logado = (Usuario) usuarioLogado;

        if (usuario.isPresent() && !Objects.equals(logado.getId(), usuario.get().getId())) {
            usuarioRepository.delete(usuario.get());
            return true;
        }

        return false;
    }

    public UsuarioDTO recuperarPorId(Long id) {
        var usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            var usuario = usuarioOptional.get();
            return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
        }

        return null;
    }

    public UsuarioDTO editar(Long id, UsuarioRequestDTO usuarioRequestDTO) {
        var usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            return null;
        }

        Usuario usuarioRecuperado = usuario.get();
        usuarioRecuperado.setNome(usuarioRequestDTO.nome());
        usuarioRecuperado.setEmail(usuarioRequestDTO.email());

        usuarioRepository.save(usuarioRecuperado);

        return modelMapper.map(usuarioRecuperado, UsuarioDTO.class);
    }
}
