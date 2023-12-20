package com.gouveia.imdb.dto;

import com.gouveia.imdb.enums.Role;

public record RegistroUsuarioDTO(String nome, String email, String senha) {
}
