package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.backend.models.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
