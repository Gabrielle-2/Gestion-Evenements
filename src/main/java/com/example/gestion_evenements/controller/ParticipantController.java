package com.example.gestion_evenements.controller;

import com.example.gestion_evenements.model.Participant;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gestion_evenements.model.ParticipantDto;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final EvenementService evenementService;

    public ParticipantController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @PostMapping // Accepte POST sur /api/participants
    public ResponseEntity<SuccessResponse> ajouterParticipant(@RequestBody ParticipantDto participantDto) {
        evenementService.ajouterParticipant(participantDto.getNom(), participantDto.getEmail());
        return ResponseEntity.ok(new SuccessResponse("Participant ajouté avec succès", null));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getParticipantById(@PathVariable String id) {
        Participant participant = evenementService.rechercherParticipant(id);
        if (participant == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse("Participant non trouvé", null));
        }
        return ResponseEntity.ok(new SuccessResponse("Participant trouvé", participant));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllParticipants() {
        Map<String, Participant> participants = evenementService.getAllParticipants();
        return ResponseEntity.ok(new SuccessResponse("Tous les participants récupérés", participants != null ? participants.values() : null));
    }

    @GetMapping("/evenement/{evenementId}")
    public ResponseEntity<SuccessResponse> getParticipantsParEvenement(@PathVariable String evenementId) {
        List<Participant> participants = evenementService.rechercherParticipantsParEvenement(evenementId);
        return ResponseEntity.ok(new SuccessResponse("Participants pour l'événement récupérés", participants));
    }
}
