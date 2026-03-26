package br.com.bsantos.screenmatch.repositories;

import br.com.bsantos.screenmatch.models.Categoria;
import br.com.bsantos.screenmatch.models.Episodio;
import br.com.bsantos.screenmatch.models.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int maxTemporada, double avaliacaoMin);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :maxTemporada AND s.avaliacao >= :avaliacaoMin")
    List<Serie> filtrarMaxTemporadasEMinAvaliacao(int maxTemporada, double avaliacaoMin);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trecho%")
    List<Episodio> buscarEpisodiosPorTrecho(String trecho);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serieBuscada ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosSerie(Serie serieBuscada);
}
