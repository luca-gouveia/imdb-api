package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.AvaliacaoDTO;
import com.gouveia.imdb.dto.CatalogoItemDTO;
import com.gouveia.imdb.dto.CatalogoItemResponseDTO;
import com.gouveia.imdb.dto.GeneroDTO;
import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/catalogo")
public class CatalogoControlller {

    @Autowired
    CatalogoService catalogoService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void criarTitulo(@RequestBody CatalogoItemDTO catalogoItemDTO) {
        catalogoService.salvar(catalogoItemDTO);
    }

    @PostMapping("/avaliar")
    @ResponseStatus(value = HttpStatus.OK)
    public void avaliarTitulo(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        catalogoService.avaliar(avaliacaoDTO);
    }

    @GetMapping
    public ResponseEntity<Page<CatalogoItemResponseDTO>> recuperarTodos(@PageableDefault(value = 50) Pageable pageable) {
        var itens = catalogoService.recuperarTodos(pageable);

        for (CatalogoItemResponseDTO catalogoItemResponseDTO : itens) {
            catalogoItemResponseDTO.add(linkTo(methodOn(CatalogoControlller.class).recuperarPorId(catalogoItemResponseDTO.getId())).withSelfRel());
        }

        return new ResponseEntity<Page<CatalogoItemResponseDTO>>(itens, HttpStatus.OK);
    }

    @GetMapping("/generos")
    public ResponseEntity<GeneroDTO> recuperarGeneros() {
        var generos = Genero.values();

        List<String> generosNome = Arrays.stream(generos)
                .map(Genero::getDescricao).toList();

        GeneroDTO generoDTO = new GeneroDTO(generosNome);

        return new ResponseEntity<GeneroDTO>(generoDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoItemResponseDTO> recuperarPorId(@PathVariable("id") Long id) {
        var item = catalogoService.recuperarPorId(id);

        if (item != null) {
            item.add(linkTo(methodOn(CatalogoControlller.class).recuperarTodos(null)).withRel(IanaLinkRelations.COLLECTION));

            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id") Long id) {
        var isDeletado =  catalogoService.deletar(id);

        if (isDeletado) {
            return ResponseEntity.ok().body("título removido!");
        } else {
            throw new RuntimeException("Não foi possível remover");
        }
    }
}
