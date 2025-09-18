package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.History;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.SerieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {


    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> getRecommendation(String peopleid) {
        Long id = Long.parseLong(peopleid);
        List<History> histories = historyRepository.findByPeopleId(id);
        int drama = 0;
        int anime = 0;
        int sciFi = 0;
        int fantasy = 0;
        int comedy = 0;
        int historical = 0;
        int mystery = 0;

        for (History h : histories) {
            String g = h.getSerie().getGender().toLowerCase();
            if (g.equals("drama")) drama++;
            else if (g.equals("anime")) anime++;
            else if (g.equals("sci-fi")) sciFi++;
            else if (g.equals("fantasy")) fantasy++;
            else if (g.equals("comedy")) comedy++;
            else if (g.equals("historical")) historical++;
            else if (g.equals("mystery")) mystery++;
        }

        String[] genres = {"drama","anime","sci-fi","fantasy","comedy","historical","mystery"};
        int[] counts = {drama, anime, sciFi, fantasy, comedy, historical, mystery};

        List<String> topGenres = new ArrayList<>();

        for (int j = 0; j < 3; j++) {
            int maxIndex = -1;
            int maxValue = -1;
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] > maxValue) {
                    maxValue = counts[i];
                    maxIndex = i;
                }
            }
            if (maxIndex != -1 && counts[maxIndex] > 0) {
                topGenres.add(genres[maxIndex]);
                counts[maxIndex] = -1;
            }
        }
        List<Serie> newSerie = new ArrayList<>();
        for (String g : topGenres) {
            newSerie.addAll(serieRepository.findByGender(g));
        }

        return newSerie;
    }
}
