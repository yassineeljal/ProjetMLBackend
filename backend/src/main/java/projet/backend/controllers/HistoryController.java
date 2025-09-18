package projet.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.backend.models.History;
import projet.backend.services.HistoryService;

import java.util.List;

@RestController
@RequestMapping("/history")
@CrossOrigin
public class HistoryController {
    @Autowired
    HistoryService historyService;

    @GetMapping("/{id}/history")
    public List<History> getPeopleHistory(@PathVariable String id) {
        return historyService.getPeopleHistory(id);
    }
    @PostMapping("/{id}/history/{serieId}")
    public boolean addSerieToHistory(@PathVariable String peopleId, @PathVariable String serieId) {
        return historyService.addToHistory(peopleId, serieId);
    }

}
