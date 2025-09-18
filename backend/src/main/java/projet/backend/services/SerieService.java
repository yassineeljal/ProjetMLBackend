package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.Serie;
import projet.backend.repositories.SerieRepository;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    public Serie getSerieById(String id) {
        Long newId = Long.parseLong(id);
        return serieRepository.findById(newId).get();
    }

    public boolean addSerie(Serie serie) {
        serieRepository.save(serie);
        return true;
    }
    public void updateSerie(Serie serie) {
        if (serie.getId() == null) return ;
        Serie newSerie = serieRepository.findSerieById(serie.getId());
        if(newSerie != null) {
            if (serie.getTitle() != null && !serie.getTitle().isBlank()) {
                newSerie.setTitle(serie.getTitle());
            }
            if (serie.getGender() != null && !serie.getGender().isBlank()) {
                newSerie.setGender(serie.getGender());
            }
            if (serie.getNbEpisodes() > 0) {
                newSerie.setNbEpisodes(serie.getNbEpisodes());
            }
            if (serie.getNote() != null && !serie.getNote().isBlank()) {
                newSerie.setNote(serie.getNote());
            }
            serieRepository.save(newSerie);
        }
    }
    public void deleteSerie(String id) {
        Long newId = Long.parseLong(id);
        Serie serie = serieRepository.findSerieById(newId);
        if (serie != null) {
            serieRepository.delete(serie);
        }
    }
}
