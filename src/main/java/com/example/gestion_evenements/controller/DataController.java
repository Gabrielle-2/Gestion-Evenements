package com.example.gestion_evenements.controller;

import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final EvenementService evenementService;

    public DataController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> saveToFile() throws IOException {
        evenementService.saveToFile();
        return ResponseEntity.ok(new SuccessResponse("Données sauvegardées avec succès", null));
    }

    @PostMapping("/load")
    public ResponseEntity<SuccessResponse> loadFromFile() throws IOException {
        evenementService.loadFromFile();
        return ResponseEntity.ok(new SuccessResponse("Données chargées avec succès", null));
    }
}
