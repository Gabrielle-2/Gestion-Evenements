package com.example.gestion_evenements.controller;

import com.example.gestion_evenements.exception.CapaciteMaxAtteinteException;
import com.example.gestion_evenements.model.Organisateur;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gestion_evenements.model.ConferenceDto; // Importe tes DTOs
import com.example.gestion_evenements.model.ConcertDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/events")
public class EvenementController {

    private final EvenementService evenementService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllEvenements() {
        SuccessResponse response = evenementService.getAllEvenements();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getEvenementById(@PathVariable String id) {
        SuccessResponse response = evenementService.rechercherEvenement(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/conference")
    public ResponseEntity<SuccessResponse> ajouterConference(@RequestBody ConferenceDto conferenceDto) {
        Organisateur organisateur = evenementService.rechercherOrganisateur(conferenceDto.getOrganisateurId());
        if (organisateur == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse("Organisateur non trouvé pour ID: " + conferenceDto.getOrganisateurId(), null));
        }
        SuccessResponse response = evenementService.ajouterConference(
                conferenceDto.getNom(),
                conferenceDto.getDate(),
                conferenceDto.getLieu(),
                conferenceDto.getCapaciteMax(),
                conferenceDto.getTheme(),
                organisateur
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/concert")
    public ResponseEntity<SuccessResponse> ajouterConcert(@RequestBody ConcertDto concertDto) {
        Organisateur organisateur = evenementService.rechercherOrganisateur(concertDto.getOrganisateurId());
        if (organisateur == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse("Organisateur non trouvé pour ID: " + concertDto.getOrganisateurId(), null));
        }
        SuccessResponse response = evenementService.ajouterConcert(
                concertDto.getNom(),
                concertDto.getDate(),
                concertDto.getLieu(),
                concertDto.getCapaciteMax(),
                concertDto.getArtiste(), // Assure-toi que ConcertDto a getArtiste() et getGenreMusical()
                concertDto.getGenreMusical(),
                organisateur
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> supprimerEvenement(@PathVariable String id) {
        SuccessResponse response = evenementService.supprimerEvenement(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/a-venir")
    public ResponseEntity<SuccessResponse> getEvenementsAVenir() {
        SuccessResponse response = evenementService.rechercherEvenementsAVenir();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/lieu/{lieu}")
    public ResponseEntity<SuccessResponse> getEvenementsParLieu(@PathVariable String lieu) {
        SuccessResponse response = evenementService.rechercherEvenementsParLieu(lieu);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{evenementId}/participants/{participantId}")
    public ResponseEntity<SuccessResponse> inscrireParticipant(
            @PathVariable String participantId,
            @PathVariable String evenementId) throws CapaciteMaxAtteinteException {
        SuccessResponse response = evenementService.inscrireParticipant(participantId, evenementId);
        return ResponseEntity.ok(response);
    }
}