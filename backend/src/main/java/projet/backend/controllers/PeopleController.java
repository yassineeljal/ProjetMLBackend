package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.People;
import projet.backend.services.PeopleService;

import java.util.List;

@RestController
@RequestMapping("/people")
@CrossOrigin
public class PeopleController {

    @Autowired
    PeopleService peopleService;

    @PostMapping("/addUser")
    public boolean addUser(@RequestBody People people) {
        return peopleService.addPeople(people);
    }

    @PostMapping("/findUser/{name}")
    public List<People> findUser(@PathVariable("name") String name){
        return peopleService.getPeopleByName(name);
    };

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") String id){
        peopleService.deletePeople(id);
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody People people){
        peopleService.updatePeople(people);
    }

}
