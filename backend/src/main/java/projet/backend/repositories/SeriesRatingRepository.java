package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projet.backend.models.SeriesRating;

import java.util.List;

public interface SeriesRatingRepository extends JpaRepository<SeriesRating, Long> {
    SeriesRating findByPeopleIdAndSerieId(Long peopleId, Long serieId);
    List<SeriesRating> findBySerieId(Long serieId);

    @Query("select avg(r.value) from SeriesRating r where r.serie.id = :serieId")
    Double averageForSerie(Long serieId);
}
