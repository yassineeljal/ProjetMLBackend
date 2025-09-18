package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.History;
import projet.backend.models.People;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.PeopleRepository;
import projet.backend.repositories.SerieRepository;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    SerieRepository serieRepository;

    public List<History> getPeopleHistory(String id) {
        Long newId = Long.parseLong(id);
        return historyRepository.findByPeopleId(newId);
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
