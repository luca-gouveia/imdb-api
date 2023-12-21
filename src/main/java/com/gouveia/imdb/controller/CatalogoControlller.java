package com.gouveia.imdb.controller;

import com.gouveia.imdb.dto.AvaliacaoDTO;
import com.gouveia.imdb.dto.BuscaDTO;
import com.gouveia.imdb.dto.CatalogoItemDTO;
import com.gouveia.imdb.dto.CatalogoItemResponseDTO;
import com.gouveia.imdb.dto.GeneroDTO;
import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/catalogo")
@Tag(name = "imdb-api")
@CrossOrigin(origins = "*")
public class CatalogoControlller {

    @Autowired
    CatalogoService catalogoService;

    @Operation(summary = "Cria título no catálogo", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Título cadastrado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Erro de acesso - Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da criação do título"),
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public void criarTitulo(@RequestBody CatalogoItemDTO catalogoItemDTO) {
        catalogoService.salvar(catalogoItemDTO);
    }

    @Operation(summary = "Avalia título do catálogo", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Título avaliado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Nota da avaliação do título fora do padrão (1 - 2 - 3 - 4)"),
            @ApiResponse(responseCode = "404", description = "Item do catálogo a ser avaliado não existe"),
            @ApiResponse(responseCode = "403", description = "Erro de acesso - Não autenticado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da avaliação do título - Usuário já avaliou"),
    })
    @PostMapping("/avaliar")
    @ResponseStatus(value = HttpStatus.OK)
    public void avaliarTitulo(@RequestBody AvaliacaoDTO avaliacaoDTO) {
        catalogoService.avaliar(avaliacaoDTO);
    }

    @Operation(summary = "Recupera todos os títulos cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Títulos recuperados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da recuperação dos títulos"),
    })
    @GetMapping
    public ResponseEntity<Page<CatalogoItemResponseDTO>> recuperarTodos(@ParameterObject @PageableDefault(value = 50) Pageable pageable) {
        var itens = catalogoService.recuperarTodos(pageable);

        for (CatalogoItemResponseDTO catalogoItemResponseDTO : itens) {
            catalogoItemResponseDTO.add(linkTo(methodOn(CatalogoControlller.class).recuperarPorId(catalogoItemResponseDTO.getId())).withSelfRel());
        }

        return new ResponseEntity<Page<CatalogoItemResponseDTO>>(itens, HttpStatus.OK);
    }

    @Operation(summary = "Recuperar os títulos cadastrados com critérios de busca (Título | Diretor | Atores | Gênero)", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Títulos filtrados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da recuperação dos títulos"),
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<CatalogoItemResponseDTO>> buscar(@ParameterObject BuscaDTO buscaDTO,
                                                                @PageableDefault(value = 50) @ParameterObject Pageable pageable) {
        var itens = catalogoService.buscar(buscaDTO, pageable);

        for (CatalogoItemResponseDTO catalogoItemResponseDTO : itens) {
            catalogoItemResponseDTO.add(linkTo(methodOn(CatalogoControlller.class).recuperarPorId(catalogoItemResponseDTO.getId())).withSelfRel());
        }

        return new ResponseEntity<Page<CatalogoItemResponseDTO>>(itens, HttpStatus.OK);
    }

    @Operation(summary = "Recuperar os gêneros existentes e válidos para o cadastro de títulos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gêneros recuperados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da recuperação dos gêneros"),
    })
    @GetMapping("/generos")
    public ResponseEntity<GeneroDTO> recuperarGeneros() {
        var generos = Genero.values();

        List<String> generosNome = Arrays.stream(generos)
                .map(Genero::getDescricao).toList();

        GeneroDTO generoDTO = new GeneroDTO(generosNome);

        return new ResponseEntity<GeneroDTO>(generoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Recupera título por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gêneros recuperados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Título por ID não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o processamento da recuperação do título"),
    })
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

    @Operation(summary = "Realiza a deleção física do título do catálogo", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Erro de acesso - Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a remoção do título"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id") Long id) {
        var isDeletado = catalogoService.deletar(id);

        if (isDeletado) {
            return ResponseEntity.ok().body("título removido!");
        } else {
            throw new RuntimeException("Não foi possível remover");
        }
    }
}
