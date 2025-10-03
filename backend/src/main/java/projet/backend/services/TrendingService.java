package projet.backend.services;

import org.springframework.stereotype.Service;
import projet.backend.dto.TrendingItem;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.SerieRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TrendingService {

    private static final double F1 = 1.0;
    private static final double F2 = 20.0;

    private final SerieRepository series;
    private final HistoryRepository history;

    public TrendingService(SerieRepository series, HistoryRepository history) {
        this.series = series;
        this.history = history;
    }


    public List<TrendingItem> topTrending(int limit) {
        Instant since = Instant.now().minus(7, ChronoUnit.DAYS);

        List<Serie> allSeries = series.findAll();

        List<TrendingItem> result = new ArrayList<>();

        for (Serie s : allSeries) {
            if (s == null || s.getId() == null) continue;

            long views7 = history.countBySerieIdAndViewedAtAfter(s.getId(), since);
            double averageNote = parseNote(s.getNote());
            double score = (views7 * F1) + (averageNote * F2);

            TrendingItem item = new TrendingItem(
                    s.getId(),
                    s.getTitle(),
                    s.getGender(),
                    s.getNbEpisodes(),
                    views7,
                    averageNote,
                    score
            );

            result.add(item);
        }

        Collections.sort(result, new Comparator<TrendingItem>() {
            @Override
            public int compare(TrendingItem a, TrendingItem b) {
                return Double.compare(b.score(), a.score());
            }
        });

        if (limit < 0) limit = 0;
        if (limit > result.size()) limit = result.size();

        return new ArrayList<>(result.subList(0, limit));
    }

    private double parseNote(String note) {
        if (note == null) return 0.0;
        String trimmed = note.trim();
        if (trimmed.isEmpty()) return 0.0;
        try {
            return Double.parseDouble(trimmed);
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }
}
