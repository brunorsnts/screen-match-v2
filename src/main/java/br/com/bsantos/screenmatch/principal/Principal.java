package br.com.bsantos.screenmatch.principal;

import br.com.bsantos.screenmatch.models.DadosSerie;
import br.com.bsantos.screenmatch.models.DadosTemporada;
import br.com.bsantos.screenmatch.models.Episodio;
import br.com.bsantos.screenmatch.models.Serie;
import br.com.bsantos.screenmatch.repositories.SerieRepository;
import br.com.bsantos.screenmatch.services.ConsumoApi;
import br.com.bsantos.screenmatch.services.ConverteDados;

import java.util.*;
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
                    1 - Buscar séries
                    2 - Buscar episódios
                    3- Listar Séries
                    
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

    private void buscarEpisodioPorSerie(){
        listarSeries();
        System.out.print("Digite o nome da série para buscar os episódios: ");
        String nomeSerie = leitura.nextLine();
        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

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
}