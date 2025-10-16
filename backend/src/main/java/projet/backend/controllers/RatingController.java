package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.Rating;
import projet.backend.models.User;
import projet.backend.repositories.RatingRepository;
import projet.backend.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    // Évaluer un épisode
    @PostMapping("/episode/{id}")
    public ResponseEntity<?> rateEpisode(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long id,
                                         @RequestParam Integer score) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        Rating rating = ratingRepository.findByUserAndEpisodeId(user, id)
                .orElse(new Rating(user, score, id, null));

        rating.setScore(score);
        ratingRepository.save(rating);
        return ResponseEntity.ok("Évaluation enregistrée");
    }

    // Évaluer une série
    @PostMapping("/series/{id}")
    public ResponseEntity<?> rateSeries(@AuthenticationPrincipal UserDetails userDetails,
                                        @PathVariable Long id,
                                        @RequestParam Integer score) {
        User user = userRepository.findByUsername(userDetails.getUsername()).get();
        Rating rating = ratingRepository.findByUserAndSeriesId(user, id)
                .orElse(new Rating(user, score, null, id));

        rating.setScore(score);
        ratingRepository.save(rating);
        return ResponseEntity.ok("Évaluation enregistrée");
    }

    // Moyenne d’un épisode
    @GetMapping("/episode/{id}")
    public ResponseEntity<?> getEpisodeAverage(@PathVariable Long id) {
        List<Rating> ratings = ratingRepository.findByEpisodeId(id);
        double avg = ratings.stream().mapToInt(Rating::getScore).average().orElse(0);
        return ResponseEntity.ok(avg);
    }

    // Moyenne d’une série
    @GetMapping("/series/{id}")
    public ResponseEntity<?> getSeriesAverage(@PathVariable Long id) {
        List<Rating> ratings = ratingRepository.findBySeriesId(id);
        double avg = ratings.stream().mapToInt(Rating::getScore).average().orElse(0);
        return ResponseEntity.ok(avg);
    }
}
