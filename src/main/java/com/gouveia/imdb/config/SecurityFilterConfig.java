package com.gouveia.imdb.config;

import com.gouveia.imdb.exceptions.TokenErrorException;
import com.gouveia.imdb.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilterConfig extends OncePerRequestFilter {
    @Autowired
    JWTConfig jwtConfig;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var token = this.recuperarTokenRequisicao(request);

        if (token != null) {
            var email = jwtConfig.validarToken(token);

            if (!email.isEmpty()) {
                UserDetails userDetails = usuarioRepository.findByEmail(email);

                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new TokenErrorException("Token expirado");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarTokenRequisicao(HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader("Authorization");
        return authHeader == null ? null : authHeader.replace("Bearer ", "");
    }
}
