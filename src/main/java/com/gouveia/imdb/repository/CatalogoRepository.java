package com.gouveia.imdb.repository;

import com.gouveia.imdb.enums.Genero;
import com.gouveia.imdb.model.CatalogoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public interface CatalogoRepository extends JpaRepository<CatalogoItem, Long> {
    Optional<CatalogoItem> findById(long id);

    @Query("FROM filme f " +
            "WHERE LOWER(f.titulo) ILIKE %:titulo% " +
            "AND (" +
            "LOWER(f.diretor) ILIKE %:diretor% " +
            "AND " +
            "LOWER(f.atores) ILIKE %:atores% " +
            ")")
    Page<CatalogoItem> buscar(@Param("titulo") String titulo,
                              @Param("diretor") String diretor,
                              @Param("atores") String atores,
                              Pageable pageable);
}
