package com.gouveia.imdb.dto;

import com.gouveia.imdb.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CatalogoItemResponseDTO extends RepresentationModel<CatalogoItemResponseDTO> {
    private Long id;
    private String titulo;
    private List<String> genero;
    private String linkImagem;
    private String descricao;
    private String diretor;
    private String atores;
    private String imdbID;
    private double avaliacao;
    private boolean isAvaliado;

    public List<String> getGenero() {
        if (genero!= null && !genero.isEmpty()) {
            List<String> listaDescricaoGenero = new ArrayList<>();

            for (String generoDescricao : genero) {
                listaDescricaoGenero.add(Genero.valueOf(generoDescricao).getDescricao());
            }

            genero = listaDescricaoGenero;
        }

        return genero;
    }
}
