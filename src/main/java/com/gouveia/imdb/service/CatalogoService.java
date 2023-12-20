package com.gouveia.imdb.service;

import com.gouveia.imdb.dto.AvaliacaoDTO;
import com.gouveia.imdb.dto.BuscaDTO;
import com.gouveia.imdb.dto.CatalogoItemDTO;
import com.gouveia.imdb.dto.CatalogoItemResponseDTO;
import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.model.Avaliacao;
import com.gouveia.imdb.model.CatalogoItem;
import com.gouveia.imdb.model.Usuario;
import com.gouveia.imdb.repository.AvaliacaoRepository;
import com.gouveia.imdb.repository.CatalogoRepository;
import com.gouveia.imdb.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

@Service
public class CatalogoService {

    @Autowired
    CatalogoRepository catalogoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AvaliacaoRepository avaliacaoRepository;

    @Autowired
    ModelMapper modelMapper;

    public void salvar(CatalogoItemDTO catalogoItemDTO) {
        List<Genero> generos = new ArrayList<>();

        if (!catalogoItemDTO.genero().isEmpty()) {
            for (String generoString : catalogoItemDTO.genero()) {
                generos.add(Genero.recuperarEnumPorDescricao(generoString));
            }

        }

        var catalogoItem = CatalogoItem.builder()
                .titulo(catalogoItemDTO.titulo())
                .descricao(catalogoItemDTO.descricao())
                .diretor(catalogoItemDTO.diretor())
                .atores(catalogoItemDTO.atores())
                .imdbID(catalogoItemDTO.imdbID())
                .genero(generos)
                .ativo(true)
                .build();

        catalogoRepository.save(catalogoItem);
    }

    public Page<CatalogoItemResponseDTO> recuperarTodos(Pageable pageable) {
        var catalogoItemPage = catalogoRepository.findAll(pageable);
        var itensListaDTO = new ArrayList<CatalogoItemResponseDTO>();

        return new PageImpl<CatalogoItemResponseDTO>(
                this.converterEmCatalogoItemResponseDTO(catalogoItemPage.getContent(), itensListaDTO),
                pageable,
                catalogoItemPage.getTotalElements()
        );
    }

    private List<CatalogoItemResponseDTO> converterEmCatalogoItemResponseDTO(List<CatalogoItem> itens, ArrayList<CatalogoItemResponseDTO> itensListaDTO) {
        for (CatalogoItem catalogoItem : itens) {
            itensListaDTO.add(modelMapper.map(catalogoItem, CatalogoItemResponseDTO.class));
        }

        return itensListaDTO;
    }

    public Boolean deletar(Long id) {
        Optional<CatalogoItem> catalogoItem = catalogoRepository.findById(id);

        if (catalogoItem.isPresent()) {
            catalogoRepository.delete(catalogoItem.get());
            return true;
        }

        return false;
    }

    public CatalogoItemResponseDTO recuperarPorId(Long id) {
        var itemOptional = catalogoRepository.findById(id);
        var usuarioLogado = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            usuarioLogado = null;
        }

        if (itemOptional.isPresent()) {
            var item = itemOptional.get();
            var notaMedia = this.calcularMediaAvaliacao(itemOptional.get().getId());

            var isAvaliado = usuarioLogado != null && this.verificaSeUsuarioJaAvaliou(itemOptional.get().getId(), ((Usuario) usuarioLogado).getId());

            CatalogoItemResponseDTO catalogoItem = modelMapper.map(item, CatalogoItemResponseDTO.class);

            catalogoItem.setAvaliacao(notaMedia);
            catalogoItem.setAvaliado(isAvaliado);

            return catalogoItem;
        }

        return null;
    }

    public boolean verificaSeUsuarioJaAvaliou(Long catalogoItemId, Long usuarioId) {
        List<Avaliacao> avaliacaoDTOS = avaliacaoRepository.findAllByCatalogoItemIdAndUsuarioId(catalogoItemId, usuarioId);
        return !avaliacaoDTOS.isEmpty();
    }

    public double calcularMediaAvaliacao(Long catalogoItemId) {
        List<Avaliacao> avaliacaoDTOS = avaliacaoRepository.findAllByCatalogoItemId(catalogoItemId);

        if (!avaliacaoDTOS.isEmpty()) {
            var notaMediaOpt = avaliacaoDTOS.stream()
                    .mapToDouble(Avaliacao::getNota)
                    .average();

            return notaMediaOpt.isPresent() ? notaMediaOpt.getAsDouble() : 0;
        }

        return 0;
    }

    @Transactional
    public void avaliar(AvaliacaoDTO avaliacaoDTO) {

        var notasValidas = List.of(1, 2, 3, 4);
        var nota = avaliacaoDTO.nota();

        var usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Optional<Usuario> usuario = usuarioRepository.findById(usuarioLogado.getId());
        Optional<CatalogoItem> catalogoItem = catalogoRepository.findById(avaliacaoDTO.idItemCatalogo());

        if (!notasValidas.contains(nota)) {
            throw new RuntimeException("Nota fora do padrão");
        }

        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não existe");
        }

        if (catalogoItem.isEmpty()) {
            throw new RuntimeException("Título não existe");
        }

        var avaliacao = Avaliacao.builder()
                .catalogoItem(catalogoItem.get())
                .usuario(usuario.get())
                .nota(nota)
                .build();

        avaliacaoRepository.save(avaliacao);
    }

    public Page<CatalogoItemResponseDTO> buscar(BuscaDTO buscaDTO, Pageable pageable) {
        List<Genero> generoEnum = null;

        var catalogoItemPage = catalogoRepository.buscar(buscaDTO.titulo(), buscaDTO.diretor(), buscaDTO.atores(), pageable);
        var itensListaDTO = new ArrayList<CatalogoItemResponseDTO>();
        var catalogoItens = catalogoItemPage.getContent();

        if (!buscaDTO.genero().isEmpty()) {
            var genero = Genero.recuperarEnumPorDescricao(buscaDTO.genero());

            catalogoItens = catalogoItens
                    .stream()
                    .filter(catalogoItem -> catalogoItem.getGenero().contains(genero)).toList();
        }

        return new PageImpl<CatalogoItemResponseDTO>(
                this.converterEmCatalogoItemResponseDTO(catalogoItens, itensListaDTO),
                pageable,
                catalogoItemPage.getTotalElements()
        );
    }
}
