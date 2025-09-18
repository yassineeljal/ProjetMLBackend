package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        return serieService.getAllSeries();
    }

    @GetMapping("/user/{id}")
    public Serie getSeriesDetails(@PathVariable("id") String id) {
        return serieService.getSerieById(id);
    }

    @PostMapping("/addSerie")
    public boolean addUser(@RequestBody Serie serie) {
        return serieService.addSerie(serie);
    }

    @PutMapping("/serie")
    public void updateUser(@RequestBody Serie serie){
        serieService.updateSerie(serie);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") String id){
        serieService.deleteSerie(id);
    }


    @GetMapping("/search")
    public List<Serie> searchSeries(@RequestParam(required = false) String genre,@RequestParam(required = false) Integer minEpisodes ) {
        return serieService.search(genre, minEpisodes);
    }

    @GetMapping("/search/title")
    public List<Serie> searchByTitle(@RequestParam String title) {
        return serieService.searchByTitle(title);
    }

}
