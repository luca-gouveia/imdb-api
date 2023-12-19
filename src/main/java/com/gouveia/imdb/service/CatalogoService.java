package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.CatalogoItemDTO;
import com.gouveia.imdb.model.CatalogoItem;
import com.gouveia.imdb.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatalogoService {

    @Autowired
    CatalogoRepository catalogoRepository;

    public ResponseEntity<?> buscar(String titulo, String diretor, String atores, String genero, Integer pagina, int sizePage, boolean isAsc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    public Optional<CatalogoItem> apresentarTitulo(long id) {
        var titulo = catalogoRepository.findById(id);

        return titulo;
    }

    public void salvar(CatalogoItemDTO catalogoItemDTO) {

        var catalogoItem = CatalogoItem.builder()
                .titulo(catalogoItemDTO.titulo())
                .descricao(catalogoItemDTO.descricao())
                .diretor(catalogoItemDTO.diretor())
                .atores(catalogoItemDTO.atores())
                .imdbID(catalogoItemDTO.imdbID())
                .ativo(true)
                .build();

        catalogoRepository.save(catalogoItem);
    }
}
