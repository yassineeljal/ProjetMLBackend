package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.Serie;
import projet.backend.services.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
@CrossOrigin
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{id}/recommendations")
    public List<Serie> getRecommendations(@PathVariable("id") String peopleId) {
        return recommendationService.getRecommendation(peopleId);
    }
}
