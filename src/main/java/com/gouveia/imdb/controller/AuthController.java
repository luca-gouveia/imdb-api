package com.gouveia.imdb.controller;

import com.gouveia.imdb.config.JWTConfig;
import com.gouveia.imdb.dto.AuthDTO;
import com.gouveia.imdb.dto.AuthResponseDTO;
import com.gouveia.imdb.dto.RegistroUsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.UsuarioRepository;
import com.gouveia.imdb.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTConfig jwtConfig;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthDTO usuario){
        var usuarioSenha = new UsernamePasswordAuthenticationToken(usuario.email(), usuario.senha());
        var auth = this.authenticationManager.authenticate(usuarioSenha);

        var token = jwtConfig.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity autenticarUsuario(@RequestBody @Validated RegistroUsuarioDTO usuario) throws Exception {
        return authService.registrarUsuario(usuario);
    }
}
