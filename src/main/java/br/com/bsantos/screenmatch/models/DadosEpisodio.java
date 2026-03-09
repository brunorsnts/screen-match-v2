package br.com.bsantos.screenmatch.models;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
       @JsonAlias("Season") String temporada,
       @JsonAlias("Title") String titulo,
       @JsonAlias("Episode") String numeroEpisodio,
       @JsonAlias("Runtime") String duracao,
       @JsonAlias("Plot") String descricao,
       @JsonAlias("imdbRating") String avaliacao) {
}
