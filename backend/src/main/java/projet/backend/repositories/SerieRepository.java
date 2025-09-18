package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.backend.models.Serie;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    Serie findSerieById(long id);
}
