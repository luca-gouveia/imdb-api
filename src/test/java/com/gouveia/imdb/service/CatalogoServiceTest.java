package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.CatalogoItemResponseDTO;
import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.model.CatalogoItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatalogoServiceTest {

    @Autowired
    CatalogoService catalogoService;

    @Test
    void getCatalogoItemResponseDTO() {
        CatalogoItem catalogoItem = CatalogoItem.builder()
                .id(1L)
                .titulo("Filme Foo")
                .descricao("foofofofoofofooff")
                .diretor("Scorsese")
                .atores("Fulano, Cicrano")
                .genero(List.of(Genero.ACAO))
                .ativo(true)
                .build();

        CatalogoItemResponseDTO catalogoItemResponseDTO = catalogoService.getCatalogoItemResponseDTO(catalogoItem, null);

        assertFalse(catalogoItemResponseDTO.isAvaliado());
    }
}