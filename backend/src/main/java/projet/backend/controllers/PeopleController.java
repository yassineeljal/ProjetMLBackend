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

    @PostMapping("/findUser/{name}")
    public List<People> findUser(@PathVariable String name){
        return peopleService.getPeopleByName(name);
    };

}
