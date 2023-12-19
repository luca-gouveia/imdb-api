package com.gouveia.imdb.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genero {
    ACAO("Ação"),
    AVENTURA("Aventura"),
    CINEMA_DE_ARTE("Cinema de arte"),
    CHANCHADA("Chanchada"),
    COMEDIA("Comédia"),
    COMEDIA_DE_ACAO("Comédia de ação"),
    COMEDIA_DE_TERROR("Comédia de terror"),
    COMEDIA_DRAMATICA("Comédia dramática"),
    COMEDIA_ROMANTICA("Comédia romântica"),
    DANCA("Dança"),
    DOCUMENTARIO("Documentário"),
    DOCUFICCAO("Docuficção"),
    DRAMA("Drama"),
    ESPIONAGEM("Espionagem"),
    FAROESTE("Faroeste"),
    FANTASIA("Fantasia"),
    FANTASIA_CIENTIFICA("Fantasia científica"),
    FICCAO_CIENTIFICA("Ficção científica"),
    FILMES_COM_TRUQUES("Filmes com truques"),
    FILMES_DE_GUERRA("Filmes de guerra"),
    MISTERIO("Mistério"),
    MUSICAL("Musical"),
    FILME_POLICIAL("Filme policial"),
    ROMANCE("Romance"),
    TERROR("Terror"),
    THRILLER("Thriller");

    private String descricao;

    public static Genero recuperarEnumPorDescricao(String code){
        for(Genero e : Genero.values()){
            if(e.descricao.equals(code)) return e;
        }
        return null;
    }

}