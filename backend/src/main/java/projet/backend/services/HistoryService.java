package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.History;
import projet.backend.models.People;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.PeopleRepository;
import projet.backend.repositories.SerieRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    SerieRepository serieRepository;

    public List<Serie> getPeopleHistory(String id) {
        Long newId = Long.parseLong(id);
        List<Serie> serieList = new ArrayList<>();
        List<History> histories = historyRepository.findByPeopleId(newId);
        for (History history : histories) {
            Serie serie = new Serie();
            serie.setId(history.getSerie().getId());
            serie.setGender(history.getSerie().getGender());
            serie.setNote(history.getSerie().getNote());
            serie.setTitle(history.getSerie().getTitle());
            serie.setNbEpisodes(history.getSerie().getNbEpisodes());
            serieList.add(serie);
        }
        return serieList;
    }

    public boolean addToHistory(String peopleId, String seriesId) {
        Long newPeopleId = Long.parseLong(peopleId);
        Long newSeriesId = Long.parseLong(seriesId);
        People people = peopleRepository.findPeopleById(newPeopleId);
        Serie serie = serieRepository.findSerieById(newSeriesId);
        if (people == null || serie == null) {
            return false;
        }
        History history = new History();
        history.setPeople(people);
        history.setSerie(serie);
        historyRepository.save(history);
        return true;
    }





}
