package com.example.gestion_evenements.controller;

import com.example.gestion_evenements.model.Organisateur;
import com.example.gestion_evenements.model.SuccessResponse;
import com.example.gestion_evenements.service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.gestion_evenements.model.OrganisateurDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organisateurs")
public class OrganisateurController {

    private final EvenementService evenementService;

    public OrganisateurController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @PostMapping // Accepte POST sur /api/organisateurs
    public ResponseEntity<SuccessResponse> ajouterOrganisateur(@RequestBody OrganisateurDto organisateurDto) {
        // Utilise les données du DTO
        evenementService.ajouterOrganisateur(organisateurDto.getNom(), organisateurDto.getEmail());
        return ResponseEntity.ok(new SuccessResponse("Organisateur ajouté avec succès", null));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getOrganisateurById(@PathVariable String id) {
        Organisateur organisateur = evenementService.rechercherOrganisateur(id);
        if (organisateur == null) {
            return ResponseEntity.badRequest().body(new SuccessResponse("Organisateur non trouvé", null));
        }
        return ResponseEntity.ok(new SuccessResponse("Organisateur trouvé", organisateur));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllOrganisateurs() {
        Map<String, Organisateur> organisateurs = evenementService.getAllOrganisateurs();
        return ResponseEntity.ok(new SuccessResponse("Tous les organisateurs récupérés", organisateurs != null ? organisateurs.values() : null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> supprimerOrganisateur(@PathVariable String id) {
        evenementService.supprimerOrganisateur(id);
        return ResponseEntity.ok(new SuccessResponse("Organisateur supprimé avec succès", null));
    }

    @GetMapping("/evenement/{evenementId}")
    public ResponseEntity<SuccessResponse> getOrganisateursParEvenement(@PathVariable String evenementId) {
        List<Organisateur> organisateurs = evenementService.rechercherOrganisateursParEvenement(evenementId);
        return ResponseEntity.ok(new SuccessResponse("Organisateurs pour l'événement récupérés", organisateurs));
    }
}
