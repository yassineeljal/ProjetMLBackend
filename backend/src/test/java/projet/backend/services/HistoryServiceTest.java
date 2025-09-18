package projet.backend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.backend.models.History;
import projet.backend.models.People;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.PeopleRepository;
import projet.backend.repositories.SerieRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {

    @Mock HistoryRepository historyRepository;
    @Mock PeopleRepository peopleRepository;
    @Mock SerieRepository serieRepository;

    @InjectMocks HistoryService historyService;

    private People p1;
    private Serie s1, s2;
    private History h1, h2;

    @BeforeEach
    void setup() {
        p1 = new People(); p1.setId(1L); p1.setName("Yassine"); p1.setGender("M"); p1.setAge(20);

        s1 = new Serie(); s1.setId(10L); s1.setTitle("Dark"); s1.setGender("sci-fi"); s1.setNbEpisodes(26); s1.setNote("Top");
        s2 = new Serie(); s2.setId(11L); s2.setTitle("Friends"); s2.setGender("comedy"); s2.setNbEpisodes(200); s2.setNote("Fun");

        h1 = new History(); h1.setPeople(p1); h1.setSerie(s1);
        h2 = new History(); h2.setPeople(p1); h2.setSerie(s2);
    }

    @Test
    void getPeopleHistory_returnsProjectedSeries() {
        when(historyRepository.findByPeopleId(1L)).thenReturn(Arrays.asList(h1, h2));

        List<Serie> out = historyService.getPeopleHistory("1");

        assertEquals(2, out.size());
        assertEquals(10L, out.get(0).getId());
        assertEquals("sci-fi", out.get(0).getGender());
        assertEquals("Dark", out.get(0).getTitle());
        assertEquals(26, out.get(0).getNbEpisodes());
        assertEquals("Top", out.get(0).getNote());
        assertEquals(11L, out.get(1).getId());
        assertEquals("comedy", out.get(1).getGender());
    }

    @Test
    void addToHistory_ok_whenPeopleAndSerieExist() {
        when(peopleRepository.findPeopleById(1L)).thenReturn(p1);
        when(serieRepository.findSerieById(10L)).thenReturn(s1);

        boolean ok = historyService.addToHistory("1", "10");

        assertTrue(ok);
        ArgumentCaptor<History> captor = ArgumentCaptor.forClass(History.class);
        verify(historyRepository).save(captor.capture());
        History saved = captor.getValue();
        assertEquals(1L, saved.getPeople().getId());
        assertEquals(10L, saved.getSerie().getId());
    }

    @Test
    void addToHistory_false_whenPeopleMissing() {
        when(peopleRepository.findPeopleById(1L)).thenReturn(null);
        when(serieRepository.findSerieById(10L)).thenReturn(s1);

        boolean ok = historyService.addToHistory("1", "10");

        assertFalse(ok);
        verify(historyRepository, never()).save(any());
    }

    @Test
    void addToHistory_false_whenSerieMissing() {
        when(peopleRepository.findPeopleById(1L)).thenReturn(p1);
        when(serieRepository.findSerieById(10L)).thenReturn(null);

        boolean ok = historyService.addToHistory("1", "10");

        assertFalse(ok);
        verify(historyRepository, never()).save(any());
    }
}
