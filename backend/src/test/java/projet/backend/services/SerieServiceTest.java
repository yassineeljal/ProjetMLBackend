package projet.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.backend.models.Serie;
import projet.backend.repositories.SerieRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SerieServiceTest {

    @Mock SerieRepository serieRepository;
    @InjectMocks SerieService serieService;

    @Test
    void getAllSeries_returnsAll() {
        when(serieRepository.findAll()).thenReturn(Collections.singletonList(new Serie()));
        assertEquals(1, serieService.getAllSeries().size());
    }

    @Test
    void getSerieById_usesRepositoryFindById() {
        Serie s = new Serie(); s.setId(9L);
        when(serieRepository.findById(9L)).thenReturn(Optional.of(s));

        Serie out = serieService.getSerieById("9");

        assertEquals(9L, out.getId());
    }

    @Test
    void addSerie_saves() {
        Serie s = new Serie(); s.setTitle("Dark");
        assertTrue(serieService.addSerie(s));
        verify(serieRepository).save(s);
    }

    @Test
    void updateSerie_updatesNonBlankFields() {
        Serie existing = new Serie();
        existing.setId(3L);
        existing.setTitle("Old");
        existing.setGender("drama");
        existing.setNbEpisodes(10);
        existing.setNote("note");
        when(serieRepository.findSerieById(3L)).thenReturn(existing);

        Serie incoming = new Serie();
        incoming.setId(3L);
        incoming.setTitle("New");
        incoming.setGender("sci-fi");
        incoming.setNbEpisodes(20);
        incoming.setNote("great");

        serieService.updateSerie(incoming);

        ArgumentCaptor<Serie> captor = ArgumentCaptor.forClass(Serie.class);
        verify(serieRepository).save(captor.capture());
        Serie saved = captor.getValue();
        assertEquals("New", saved.getTitle());
        assertEquals("sci-fi", saved.getGender());
        assertEquals(20, saved.getNbEpisodes());
        assertEquals("great", saved.getNote());
    }

    @Test
    void deleteSerie_deletesIfExists() {
        Serie s = new Serie(); s.setId(4L);
        when(serieRepository.findSerieById(4L)).thenReturn(s);

        serieService.deleteSerie("4");

        verify(serieRepository).delete(s);
    }

    @Test
    void search_allNull_returnsAll() {
        when(serieRepository.findAll()).thenReturn(Arrays.asList(new Serie(), new Serie()));
        List<Serie> out = serieService.search(null, null);
        assertEquals(2, out.size());
    }

    @Test
    void search_byGenreOnly() {
        Serie s = new Serie(); s.setGender("comedy");
        when(serieRepository.findByGender("comedy")).thenReturn(Collections.singletonList(s));

        List<Serie> out = serieService.search("comedy", null);

        assertEquals(1, out.size());
        assertEquals("comedy", out.get(0).getGender());
    }

    @Test
    void search_byMinEpisodesOnly() {
        Serie s1 = new Serie(); s1.setNbEpisodes(12);
        when(serieRepository.findByNbEpisodesGreaterThanEqual(10)).thenReturn(Collections.singletonList(s1));

        List<Serie> out = serieService.search(null, 10);

        assertEquals(1, out.size());
        assertTrue(out.get(0).getNbEpisodes() >= 10);
    }

    @Test
    void search_byGenreAndMinEpisodes_filtersManually() {
        Serie a = new Serie(); a.setGender("drama"); a.setNbEpisodes(8);
        Serie b = new Serie(); b.setGender("drama"); b.setNbEpisodes(15);
        when(serieRepository.findByGender("drama")).thenReturn(Arrays.asList(a, b));

        List<Serie> out = serieService.search("drama", 10);

        assertEquals(1, out.size());
        assertEquals(15, out.get(0).getNbEpisodes());
    }

    @Test
    void searchByTitle_proxiesRepoCall() {
        Serie s = new Serie(); s.setTitle("Dark");
        when(serieRepository.findByTitle("Dark")).thenReturn(Collections.singletonList(s));

        List<Serie> out = serieService.searchByTitle("Dark");

        assertEquals(1, out.size());
        assertEquals("Dark", out.get(0).getTitle());
    }
}
