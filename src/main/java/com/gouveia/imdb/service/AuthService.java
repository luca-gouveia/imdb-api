package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.RegistroUsuarioDTO;
import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.exceptions.NotFoundErrorException;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    public ResponseEntity<UsuarioDTO> registrarUsuario(RegistroUsuarioDTO usuario) {
        var hasUsuario = this.usuarioRepository.findByEmail(usuario.email()) != null;

        if(hasUsuario) {
            throw new NotFoundErrorException("Usuário já cadastrado!");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.senha());
        Usuario novoUsuario = new Usuario(usuario.nome(), usuario.email(), senhaCriptografada);

        var usuarioNovo = this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioDTO(usuarioNovo.getId(), usuarioNovo.getNome(), usuarioNovo.getEmail(), usuarioNovo.getRole()));
    }
}
