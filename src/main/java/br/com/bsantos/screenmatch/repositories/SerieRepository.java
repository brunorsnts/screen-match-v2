package br.com.bsantos.screenmatch.repositories;

import br.com.bsantos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
