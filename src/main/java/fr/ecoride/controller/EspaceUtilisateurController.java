package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.UserSpaceForm;
import fr.ecoride.dto.UserSpaceResponseDTO;
import fr.ecoride.dto.VoitureDTO;
import fr.ecoride.service.IEspaceUtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/espace-utilisateur")
public class EspaceUtilisateurController {

    private final IEspaceUtilisateurService espaceUtilisateurService;

    public EspaceUtilisateurController(IEspaceUtilisateurService espaceUtilisateurService) {
        this.espaceUtilisateurService = espaceUtilisateurService;
    }

    @PostMapping
    public ResponseEntity<?> enregistrerInfos(@RequestBody UserSpaceForm form, Principal principal) {
        espaceUtilisateurService.enregistrer(form, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserSpaceResponseDTO> getInfos(@AuthenticationPrincipal UtilisateurDetails userDetails) {
        return ResponseEntity.ok(espaceUtilisateurService.getInfos(userDetails.getUtilisateur()));
    }

    @DeleteMapping
    public ResponseEntity<Boolean> supprimerVoiture(@RequestBody VoitureDTO voitureDTO, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        return ResponseEntity.ok(espaceUtilisateurService.supprimerVoiture(voitureDTO, userDetails.getUtilisateur()));
    }

}
