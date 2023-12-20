package com.gouveia.imdb.repository;

import com.gouveia.imdb.model.CatalogoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogoRepository extends JpaRepository<CatalogoItem, Long> {
    Optional<CatalogoItem> findById(long id);
}
