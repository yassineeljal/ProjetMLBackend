package projet.backend.startup;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import projet.backend.models.*;
import projet.backend.repositories.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Component
public class DataAuto implements CommandLineRunner {

    private static final int MAX_EPISODES_PER_SERIE = 20;
    private static final int VIEWS_PER_USER = 5;
    private static final int SERIES_RATINGS_PER_USER = 3;
    private static final int EPISODE_RATINGS_PER_USER = 2;

    private final PeopleRepository peopleRepo;
    private final SerieRepository serieRepo;
    private final EpisodeRepository episodeRepo;
    private final HistoryRepository historyRepo;
    private final SeriesRatingRepository seriesRatingRepo;
    private final EpisodeRatingRepository episodeRatingRepo;

    public DataAuto(PeopleRepository peopleRepo,
                    SerieRepository serieRepo,
                    EpisodeRepository episodeRepo,
                    HistoryRepository historyRepo,
                    SeriesRatingRepository seriesRatingRepo,
                    EpisodeRatingRepository episodeRatingRepo) {
        this.peopleRepo = peopleRepo;
        this.serieRepo = serieRepo;
        this.episodeRepo = episodeRepo;
        this.historyRepo = historyRepo;
        this.seriesRatingRepo = seriesRatingRepo;
        this.episodeRatingRepo = episodeRatingRepo;
    }

    @Override
    public void run(String... args) {
        boolean hasPeople = peopleRepo.count() > 0;
        boolean hasSeries = serieRepo.count() > 0;
        if (!hasPeople || !hasSeries) {
            return;
        }

        Random rnd = new Random();
        List<People> users = peopleRepo.findAll();
        List<Serie> series = serieRepo.findAll();

        if (episodeRepo.count() == 0) {
            for (Serie s : series) {
                int toCreate = Math.min(s.getNbEpisodes(), MAX_EPISODES_PER_SERIE);
                for (int i = 1; i <= toCreate; i++) {
                    Episode e = new Episode();
                    e.setSerie(s);
                    e.setSeasonNumber(1);
                    e.setEpisodeNumber(i);
                    e.setTitle(s.getTitle() + " E" + i);
                    episodeRepo.save(e);
                }
            }
        }

        if (historyRepo.count() == 0) {
            for (People u : users) {
                for (int i = 0; i < VIEWS_PER_USER; i++) {
                    Serie s = series.get(rnd.nextInt(series.size()));
                    History h = new History();
                    h.setPeople(u);
                    h.setSerie(s);
                    h.setViewedAt(randomInstantLastDays(7, rnd));
                    historyRepo.save(h);
                }
            }
        }

        if (seriesRatingRepo.count() == 0) {
            for (People u : users) {
                for (int i = 0; i < SERIES_RATINGS_PER_USER; i++) {
                    Serie s = series.get(rnd.nextInt(series.size()));
                    SeriesRating r = new SeriesRating();
                    r.setPeople(u);
                    r.setSerie(s);
                    r.setValue(1 + rnd.nextInt(5));
                    seriesRatingRepo.save(r);
                }
            }
        }

        if (episodeRatingRepo.count() == 0) {
            List<Episode> allEpisodes = episodeRepo.findAll();
            if (!allEpisodes.isEmpty()) {
                for (People u : users) {
                    for (int i = 0; i < EPISODE_RATINGS_PER_USER; i++) {
                        Episode e = allEpisodes.get(rnd.nextInt(allEpisodes.size()));
                        EpisodeRating r = new EpisodeRating();
                        r.setPeople(u);
                        r.setEpisode(e);
                        r.setValue(1 + rnd.nextInt(5));
                        episodeRatingRepo.save(r);
                    }
                }
            }
        }
    }

    private Instant randomInstantLastDays(int lastDays, Random rnd) {
        if (lastDays <= 0) return Instant.now();
        int daysAgo = rnd.nextInt(lastDays + 1);
        return Instant.now().minus(daysAgo, ChronoUnit.DAYS);
    }
}
