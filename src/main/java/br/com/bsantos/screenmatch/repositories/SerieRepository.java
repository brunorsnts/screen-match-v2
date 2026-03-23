package br.com.bsantos.screenmatch.repositories;

import br.com.bsantos.screenmatch.models.Categoria;
import br.com.bsantos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);
}
