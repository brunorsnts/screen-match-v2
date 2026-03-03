package br.com.bsantos.screenmatch;

import br.com.bsantos.screenmatch.models.DadosSerie;
import br.com.bsantos.screenmatch.services.ConsumoAPI;
import br.com.bsantos.screenmatch.services.ConverterParaObjeto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        String json = ConsumoAPI.obterDados("http://www.omdbapi.com/?t=the+walking+dead&apiKey=e30b85e3");
        ConverterParaObjeto convertor = new ConverterParaObjeto();
        DadosSerie serie = convertor.obterDados(json, DadosSerie.class);
        System.out.println(serie);
    }
}
