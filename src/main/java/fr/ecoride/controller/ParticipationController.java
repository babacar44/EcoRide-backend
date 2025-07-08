package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.ParticipationRequestDTO;
import fr.ecoride.service.IParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ParticipationController {

    private final IParticipationService participationService;

    @PostMapping
    public ResponseEntity<?> participer(@RequestBody ParticipationRequestDTO request, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        participationService.participer(request.getCovoiturageId(), userDetails.getUtilisateur().getUtilisateurId());
        return ResponseEntity.ok().build();
    }
}
