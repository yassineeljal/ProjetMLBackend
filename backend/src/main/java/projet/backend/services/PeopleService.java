package projet.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projet.backend.models.People;
import projet.backend.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public List<People> getPeopleByName(String name) {
        List<People> peoples = new ArrayList<>();
        for (People people : peopleRepository.findAll()) {
            if (people.getName().startsWith(name)) {
                peoples.add(people);
            }
        }
        return peoples;
    }
}
