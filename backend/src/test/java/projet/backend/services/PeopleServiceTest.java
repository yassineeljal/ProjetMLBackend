package projet.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projet.backend.models.People;
import projet.backend.repositories.PeopleRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    @Mock PeopleRepository peopleRepository;
    @InjectMocks PeopleService peopleService;

    @Test
    void getPeopleByName_startsWith_filtering() {
        People p1 = new People(); p1.setName("Yassine");
        People p2 = new People(); p2.setName("Youssef");
        People p3 = new People(); p3.setName("Adam");
        when(peopleRepository.findAll()).thenReturn(Arrays.asList(p1, p2, p3));

        List<People> result = peopleService.getPeopleByName("Yo");

        assertEquals(1, result.size());
        assertEquals("Youssef", result.get(0).getName());
    }

    @Test
    void addPeople_saves() {
        People p = new People(); p.setName("Khalil");
        boolean ok = peopleService.addPeople(p);
        assertTrue(ok);
        verify(peopleRepository).save(p);
    }

    @Test
    void deletePeople_deletesByFoundEntity() {
        People p = new People(); p.setId(5L); p.setName("Mehdi");
        when(peopleRepository.findPeopleById(5L)).thenReturn(p);

        peopleService.deletePeople("5");

        verify(peopleRepository).delete(p);
    }

    @Test
    void updatePeople_updatesOnlyProvidedFields() {
        People existing = new People(); existing.setId(7L); existing.setName("Old"); existing.setGender("M"); existing.setAge(18);
        when(peopleRepository.findPeopleById(7L)).thenReturn(existing);

        People incoming = new People(); incoming.setId(7L); incoming.setName("New"); incoming.setGender(""); incoming.setAge(22);

        peopleService.updatePeople(incoming);

        ArgumentCaptor<People> captor = ArgumentCaptor.forClass(People.class);
        verify(peopleRepository).save(captor.capture());
        People saved = captor.getValue();

        assertEquals("New", saved.getName());
        assertEquals("M", saved.getGender());
        assertEquals(22, saved.getAge());
    }
}
