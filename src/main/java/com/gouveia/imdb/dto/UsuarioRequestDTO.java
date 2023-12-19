package com.gouveia.imdb.dto;

import com.gouveia.imdb.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

public record UsuarioRequestDTO(String nome, String email) {
}
