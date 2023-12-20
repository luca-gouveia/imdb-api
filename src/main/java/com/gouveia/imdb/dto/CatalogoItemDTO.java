package com.gouveia.imdb.dto;

import java.util.List;

public record CatalogoItemDTO(String titulo, List<String> genero, String diretor, String atores, String descricao, String imdbID) {
}
