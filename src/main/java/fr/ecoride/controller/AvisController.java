package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.AvisRequestDTO;
import fr.ecoride.service.IAvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AvisController {


    private final IAvisService avisService;

    @PostMapping
    public ResponseEntity<?> envoyerAvis(@RequestBody AvisRequestDTO dto,
                                         @AuthenticationPrincipal UtilisateurDetails userDetails) {
        avisService.envoyerAvis(dto, userDetails.getUtilisateur());
        return ResponseEntity.ok().build();
    }
}
