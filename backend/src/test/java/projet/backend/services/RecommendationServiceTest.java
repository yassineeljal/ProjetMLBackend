package projet.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.backend.models.History;
import projet.backend.models.People;
import projet.backend.models.Serie;
import projet.backend.repositories.HistoryRepository;
import projet.backend.repositories.SerieRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock HistoryRepository historyRepository;
    @Mock SerieRepository serieRepository;

    @InjectMocks RecommendationService recommendationService;

    @Test
    void getRecommendation_returnsSeriesFromTop3Genres() {
        People user = new People(); user.setId(1L);

        Serie s1 = new Serie(); s1.setId(1L); s1.setTitle("D1"); s1.setGender("drama");
        Serie s2 = new Serie(); s2.setId(2L); s2.setTitle("D2"); s2.setGender("drama");
        Serie s3 = new Serie(); s3.setId(3L); s3.setTitle("SF1"); s3.setGender("sci-fi");
        Serie s4 = new Serie(); s4.setId(4L); s4.setTitle("COM1"); s4.setGender("comedy");

        History h1 = new History(); h1.setPeople(user); h1.setSerie(s1);
        History h2 = new History(); h2.setPeople(user); h2.setSerie(s2);
        History h3 = new History(); h3.setPeople(user); h3.setSerie(s3);
        History h4 = new History(); h4.setPeople(user); h4.setSerie(s4);

        when(historyRepository.findByPeopleId(1L)).thenReturn(Arrays.asList(h1, h2, h3, h4));
        when(serieRepository.findByGender("drama")).thenReturn(Arrays.asList(s1, s2));
        when(serieRepository.findByGender("sci-fi")).thenReturn(Collections.singletonList(s3));
        when(serieRepository.findByGender("comedy")).thenReturn(Collections.singletonList(s4));

        List<Serie> recs = recommendationService.getRecommendation("1");

        assertEquals(4, recs.size());
        assertTrue(recs.stream().anyMatch(s -> s.getTitle().equals("D1")));
        assertTrue(recs.stream().anyMatch(s -> s.getTitle().equals("D2")));
        assertTrue(recs.stream().anyMatch(s -> s.getTitle().equals("SF1")));
        assertTrue(recs.stream().anyMatch(s -> s.getTitle().equals("COM1")));
    }

    @Test
    void getRecommendation_emptyWhenNoHistory() {
        when(historyRepository.findByPeopleId(2L)).thenReturn(Collections.emptyList());

        List<Serie> recs = recommendationService.getRecommendation("2");

        assertTrue(recs.isEmpty());
        verify(serieRepository, never()).findByGender(anyString());
    }
}
