package br.com.bsantos.screenmatch;

import br.com.bsantos.screenmatch.principal.Principal;
import br.com.bsantos.screenmatch.repositories.SerieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    private final SerieRepository serieRepository;

    public ScreenmatchApplication(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Locale.setDefault(Locale.US);
        Principal principal = new Principal(serieRepository);
        principal.exibeMenu();
    }
}
