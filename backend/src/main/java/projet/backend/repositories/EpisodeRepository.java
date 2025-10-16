package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.backend.models.Episode;
import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findBySerieId(Long serieId);
}
