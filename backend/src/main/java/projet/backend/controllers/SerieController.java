package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.People;
import projet.backend.models.Serie;
import projet.backend.services.SerieService;

import java.util.List;

@RestController
@RequestMapping("/series")
@CrossOrigin
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping
    public List<Serie> getAllSeries() {
        return serieService.
    }

    @GetMapping("/user/{id}")
    public List<Serie> getSeriesDetails(@PathVariable("id") String id) {
        return serieService.
    }

    @PostMapping("/addSerie")
    public boolean addUser(@RequestBody Serie serie) {
        return serieService.
    }

    @PutMapping("/serie")
    public void updateUser(@RequestBody Serie serie){
        serieService.
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") String id){
        serieService.
    }






}
