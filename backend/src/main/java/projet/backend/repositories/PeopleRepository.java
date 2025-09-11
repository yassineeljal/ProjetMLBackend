package projet.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.backend.models.People;

public interface PeopleRepository extends JpaRepository<People, Long> {
    People findPeopleByName(String name);
}
