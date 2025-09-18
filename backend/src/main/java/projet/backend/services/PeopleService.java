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

    public boolean addPeople(People people) {
        peopleRepository.save(people);
        return true;
    }

    public void deletePeople(String id) {
       Long newId = Long.parseLong(id);
       People people = peopleRepository.findPeopleById(newId);
       peopleRepository.delete(people);
    }

    public void updatePeople(People people) {
        People editPeople = peopleRepository.findPeopleById(people.getId());
        if (editPeople != null) {
            if (!people.getName().isBlank()) {
                editPeople.setName(people.getName());
            }
            if (!people.getGender().isBlank()){
                editPeople.setGender(people.getGender());
            }
            if (people.getAge() != 0){
                editPeople.setAge(people.getAge());
            }
            peopleRepository.save(editPeople);

        }
    }

}
