package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.backend.models.Serie;

import java.util.List;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    Serie findSerieById(long id);
    List<Serie> findByGender(String genre);
    List<Serie> findByNbEpisodesGreaterThanEqual(int nbEpisodes);
    List<Serie> findByTitle(String title);

}
