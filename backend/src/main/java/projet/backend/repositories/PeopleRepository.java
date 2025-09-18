package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projet.backend.models.People;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    People findPeopleById(long id);
    People findPeopleByName(String name);
    People findPeopleByAge(int age);
    People findPeopleByGender(String gender);
}
