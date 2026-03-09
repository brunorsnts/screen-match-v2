package br.com.bsantos.screenmatch.principal;

import br.com.bsantos.screenmatch.entities.Episodio;
import br.com.bsantos.screenmatch.models.DadosEpisodio;
import br.com.bsantos.screenmatch.models.DadosSerie;
import br.com.bsantos.screenmatch.models.DadosTemporada;
import br.com.bsantos.screenmatch.services.ConsumoAPI;
import br.com.bsantos.screenmatch.services.ConverterParaObjeto;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private static final Scanner SC = new Scanner(System.in);
    private static final ConverterParaObjeto CONVERSOR = new ConverterParaObjeto();
    private static final String ENDERECO_API = "http://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apiKey=e30b85e3";
    private static int numTemporada;

    public static void exibeMenu() {
        System.out.print("Pesquise por uma série: ");
        String serie = URLEncoder.encode(SC.nextLine());
        String json = ConsumoAPI.obterDados(ENDERECO_API + serie + API_KEY);
        DadosSerie dadosSerie = CONVERSOR.obterDados(json, DadosSerie.class);
        System.out.println();

        // Dados da série
        System.out.println(" ".repeat(8) + dadosSerie.titulo());
        System.out.println("=".repeat(30));
        System.out.println("Temporadas: " + dadosSerie.totalTemporadas());
        System.out.println("Avaliação: " + dadosSerie.avaliacao());
        System.out.println("=".repeat(30));
        System.out.println();

        System.out.print("Temporada: ");
        int temporada = SC.nextInt();
        json = ConsumoAPI.obterDados(ENDERECO_API + serie + "&season=" + temporada + API_KEY);
        DadosTemporada dadosTemporada = CONVERSOR.obterDados(json, DadosTemporada.class);
        System.out.println();

        // Dados da temporada
        System.out.println(" ".repeat(8) + "Temporada " + temporada);
        System.out.println("=".repeat(30));
        dadosTemporada.episodios().forEach(x -> System.out.println("Episódio " + x.numeroEpisodio()));
        System.out.println("=".repeat(30));
        System.out.println();

        System.out.print("Episódio: ");
        int episodio = SC.nextInt();
        json = ConsumoAPI.obterDados(ENDERECO_API + serie + "&season=" + temporada + "&episode=" + episodio + API_KEY);
        DadosEpisodio dadosEpisodio = CONVERSOR.obterDados(json, DadosEpisodio.class);
        System.out.println();

        // Dados do episódio
        System.out.println(" ".repeat(8) + "Episódio " + episodio);
        System.out.println("=".repeat(60));
        System.out.println("Temporada: " + dadosEpisodio.temporada());
        System.out.println("Título: " + dadosEpisodio.titulo());
        System.out.println("Sinopse: " + dadosEpisodio.descricao());
        System.out.println("Duração: " + dadosEpisodio.duracao());
        System.out.println("=".repeat(60));
        System.out.println();

        System.out.println(" ".repeat(8) + "Top 5 melhores episódios");
        System.out.println("=".repeat(60));
        List<DadosEpisodio> dadosEpisodios = new ArrayList<>();
        dadosTemporada.episodios().forEach(ep -> {
            String newJson = ConsumoAPI.obterDados(ENDERECO_API + serie + "&season=" + temporada + "&episode=" + ep.numeroEpisodio() + API_KEY);
            DadosEpisodio newDadosEpisodio = CONVERSOR.obterDados(newJson, DadosEpisodio.class);
            dadosEpisodios.add(newDadosEpisodio);
        });

        dadosEpisodios.stream()
                .map(Episodio::new)
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(5)
                .forEach(ep -> System.out.println(ep + "\n" + "-".repeat(60)));

        System.out.println("=".repeat(60));
    }
}