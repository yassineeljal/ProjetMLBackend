package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.Serie;
import projet.backend.repositories.SerieRepository;

import java.util.ArrayList;
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
    public List<Serie> search(String genre) {
        if (genre == null || genre.isEmpty()) {
            return serieRepository.findAll();
        } else {
            return serieRepository.findByGender(genre);
        }
    }

    public List<Serie> search(String genre, Integer minEpisodes) {
        if ((genre == null || genre.isEmpty()) && minEpisodes == null) {
            return serieRepository.findAll();
        }
        else if (genre != null && !genre.isEmpty() && minEpisodes == null) {
            return serieRepository.findByGender(genre);
        }
        else if ((genre == null || genre.isEmpty()) && minEpisodes != null) {
            return serieRepository.findByNbEpisodesGreaterThanEqual(minEpisodes);
        }
        else {
            List<Serie> series = serieRepository.findByGender(genre);
            List<Serie> filteredSeries = new ArrayList<>();
            for (Serie serie : series) {
                if (serie.getNbEpisodes() >= minEpisodes) {
                    filteredSeries.add(serie);
                }
            }
            return filteredSeries;
        }
    }
    public List<Serie> searchByTitle(String title) {
        return serieRepository.findByTitle(title);
    }


}
