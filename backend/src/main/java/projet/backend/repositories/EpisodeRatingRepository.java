package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projet.backend.models.EpisodeRating;

import java.util.List;

public interface EpisodeRatingRepository extends JpaRepository<EpisodeRating, Long> {
    EpisodeRating findByPeopleIdAndEpisodeId(Long peopleId, Long episodeId);
    List<EpisodeRating> findByEpisodeId(Long episodeId);

    @Query("select avg(r.value) from EpisodeRating r where r.episode.id = :episodeId")
    Double averageForEpisode(Long episodeId);
}
