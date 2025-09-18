package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.History;
import projet.backend.models.Serie;
import projet.backend.services.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @GetMapping("/{id}/history")
    public List<Serie> getPeopleHistory(@PathVariable("id") String peopleId) {
        return historyService.getPeopleHistory(peopleId);
    }

    @PostMapping("/{id}/history/{serieId}")
    public boolean addSerieToHistory(@PathVariable("id") String peopleId, @PathVariable("serieId") String serieId) {
        return historyService.addToHistory(peopleId, serieId);
    }

}
