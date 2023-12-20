package com.gouveia.imdb.controller;

import com.gouveia.imdb.config.JWTConfig;
import com.gouveia.imdb.dto.AuthDTO;
import com.gouveia.imdb.dto.AuthResponseDTO;
import com.gouveia.imdb.dto.RegistroUsuarioDTO;
import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.exceptions.LoginErrorException;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "imdb-api")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTConfig jwtConfig;

    @Operation(summary = "Realiza o login para a recuperação de um token", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Login não foi realizado ou não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento do token"),
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO){
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authDTO.email(), authDTO.senha());

        try {
            var auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            var token = jwtConfig.gerarToken((Usuario) auth.getPrincipal());

            return ResponseEntity.ok(new AuthResponseDTO(token));
        }catch (InternalAuthenticationServiceException e) {
            throw new LoginErrorException("Erro ao logar! Verifique as credencias");
        }
    }

    @Operation(summary = "Realiza o registro do Usuário na api", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de novo usuário realizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Registro falhou por usuário já existir"),
    })
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody @Validated RegistroUsuarioDTO registroUsuarioDTO) {
        return authService.registrarUsuario(registroUsuarioDTO);
    }
}
