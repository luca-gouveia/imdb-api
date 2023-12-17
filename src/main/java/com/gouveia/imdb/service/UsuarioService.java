package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.RegistroUsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    public ResponseEntity registrarUsuario(RegistroUsuarioDTO usuario) {
        var hasUsuario = this.usuarioRepository.findByEmail(usuario.email()) != null;

        if(hasUsuario) {
            return ResponseEntity.badRequest().build();
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuario.senha());
        Usuario novoUsuario = new Usuario(usuario.nome(), usuario.email(), senhaCriptografada);

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
