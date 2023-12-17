package com.gouveia.imdb.repository;

import com.gouveia.imdb.dto.UsuarioDTO;
import com.gouveia.imdb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    UserDetails findByEmail(String email);
    Optional<Usuario> getByEmail(String email);
    List<UsuarioDTO> findAllByAtivo(Boolean ativo);
}
