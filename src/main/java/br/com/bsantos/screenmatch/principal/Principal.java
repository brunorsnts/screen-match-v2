package br.com.bsantos.screenmatch.principal;

import br.com.bsantos.screenmatch.models.DadosSerie;
import br.com.bsantos.screenmatch.models.DadosTemporada;
import br.com.bsantos.screenmatch.models.Episodio;
import br.com.bsantos.screenmatch.models.Serie;
import br.com.bsantos.screenmatch.repositories.SerieRepository;
import br.com.bsantos.screenmatch.services.ConsumoApi;
import br.com.bsantos.screenmatch.services.ConverteDados;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apiKey=" + System.getenv("OMDB_API_KEY");

    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    private SerieRepository repository;

    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar Séries
                    2 - Buscar Episódios
                    3 - Listar Séries
                    4 - Buscar Série por Título
                    5 - Buscar Série por Ator
                    6 - Top 5 Séries
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = validarInput("Selecione uma opção: ");
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeries();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    top5Series();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println();
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        repository.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeries();
        System.out.print("Digite o nome da série para buscar os episódios: ");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        if (serie.isPresent()) {
            Serie serieEncontrada = serie.get();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
            episodios.forEach(System.out::println);
        }
    }

    private int validarInput(String mensagem) {
        try {
            System.out.print(mensagem);
            return leitura.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            leitura.next();
            return -1;
        }
    }

    private void listarSeries() {
        series = repository.findAll();
        series.forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Digite o nome da série: ");
        String nomeSerie = leitura.nextLine();

        Serie serieEcontrada = repository.findByTituloContainingIgnoreCase(nomeSerie).orElseThrow(() -> new RuntimeException("Nenhuma série encontrada"));

        System.out.println("Série encontrada: " + serieEcontrada);
    }

    private void buscarSeriePorAtor() {
        System.out.print("Digite o nome do ator ou atriz: ");
        String nomeAtor = leitura.nextLine();
        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCase(nomeAtor);

        if (seriesEncontradas.isEmpty()) {
            System.out.println("Nenhuma série encontrada.");
        } else {
            System.out.println("Series encontradas: ");
            seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " | " + s.getAvaliacao()));
        }
    }

    private void top5Series() {
        AtomicInteger i = new AtomicInteger(1);
        List<Serie> top5Series = repository.findByOrderByAvaliacaoDesc();
        top5Series.forEach(s -> {
            System.out.println(i + "° " + s.getTitulo() + " | " + s.getAvaliacao());
            i.getAndIncrement();
        });
    }
}