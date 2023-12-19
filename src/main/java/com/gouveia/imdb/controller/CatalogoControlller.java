package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.AuthResponseDTO;
import com.gouveia.imdb.dto.CatalogoItemDTO;
import com.gouveia.imdb.dto.PageResponseDTO;
import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/catalogo")
public class CatalogoControlller {

    @Autowired
    CatalogoService catalogoService;

    private final static int SIZE_PAGE = 10;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void criarAnuncio(@RequestBody CatalogoItemDTO catalogoItemDTO) throws IOException {
        catalogoService.salvar(catalogoItemDTO);
    }

    @GetMapping
    public ResponseEntity criarAnuncio() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/buscar")
    public <T> ResponseEntity<PageResponseDTO<T>> filtrarFilmes(
            @RequestParam("genero") String genero,
            @RequestParam("diretor") String diretor,
            @RequestParam("atores") String atores,
            @RequestParam("titulo") String titulo,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("sort") Optional<String> sort) {

        PageResponseDTO<T> resultado = null;
        var pagina = page.orElse(0);
        boolean isAsc = sort.filter("asc"::equals).isPresent();

        if (titulo != null || diretor != null || atores != null || genero != null) {
            return (ResponseEntity<PageResponseDTO<T>>) catalogoService.buscar(titulo, diretor, atores, genero, pagina, SIZE_PAGE, isAsc);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultado);
        }
    }
}
