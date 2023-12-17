package com.gouveia.imdb.model;

import com.gouveia.imdb.enums.Genero;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "filme")
@Table(name = "filme")
@Getter
@Setter
@NoArgsConstructor
public class ItemCatalogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String titulo;
    @Column
    @Enumerated(EnumType.STRING)
    private List<Genero> genero;
    @Column
    private String linkImagem;
    @Column
    private String descricao;
    @Column
    private String diretor;
    @Column
    private String imdbID;
    @Column
    private Boolean ativo = true;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date criadoEm;
}
