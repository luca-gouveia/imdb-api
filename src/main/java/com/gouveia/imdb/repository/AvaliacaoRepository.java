package com.gouveia.imdb.repository;

import com.gouveia.imdb.dto.AvaliacaoDTO;
import com.gouveia.imdb.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Optional<AvaliacaoDTO> findById(long id);

    @Query("FROM avaliacao a WHERE a.catalogoItem.id = :catalogoItemId and a.usuario.id = :usuarioId")
    List<Avaliacao> findAllByCatalogoItemIdAndUsuarioId(@Param("catalogoItemId") Long catalogoItemId, @Param("usuarioId") Long usuarioId);

    @Query("FROM avaliacao a WHERE a.catalogoItem.id = :catalogoItemId")
    List<Avaliacao> findAllByCatalogoItemId(@Param("catalogoItemId") Long catalogoItemId);


}
