package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.backend.models.Rating;
import projet.backend.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUserAndEpisodeId(User user, Long episodeId);
    Optional<Rating> findByUserAndSeriesId(User user, Long seriesId);
    List<Rating> findByEpisodeId(Long episodeId);
    List<Rating> findBySeriesId(Long seriesId);
}
