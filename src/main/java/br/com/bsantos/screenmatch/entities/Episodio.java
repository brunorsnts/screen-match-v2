package br.com.bsantos.screenmatch.entities;

import br.com.bsantos.screenmatch.models.DadosEpisodio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodio;
    private Integer duracao;
    private String descricao;
    private Double avaliacao;

    public Episodio(DadosEpisodio dadosEpisodio) {
        this.temporada = Integer.valueOf(dadosEpisodio.temporada());
        this.titulo = dadosEpisodio.titulo();
        this.numeroEpisodio = Integer.valueOf(dadosEpisodio.numeroEpisodio());

        Pattern pattern = Pattern.compile("(\\d+) min");
        Matcher matcher = pattern.matcher(dadosEpisodio.duracao());
        if (matcher.find()) {
            this.duracao = Integer.valueOf(matcher.group(1));
        }
        this.descricao = dadosEpisodio.descricao();
        this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
    }

    public Integer getTemporada() {
        return temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    @Override
    public String toString() {
        return "Temporada: " + getTemporada() + "\n"
                + "Episódio: " + getNumeroEpisodio() + "\n"
                + "Título: " + getTitulo() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Duração: " + getDuracao() + "\n"
                + "Avaliação: " + getAvaliacao();
    }
}
