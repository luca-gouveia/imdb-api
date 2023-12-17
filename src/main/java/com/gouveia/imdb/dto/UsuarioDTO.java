package com.gouveia.imdb.dto;

import com.gouveia.imdb.enums.Role;

public record UsuarioDTO(Long id, String nome, String email, Role role) {
}
