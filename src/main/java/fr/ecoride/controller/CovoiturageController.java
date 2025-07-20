package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.CovoiturageRequestDTO;
import fr.ecoride.dto.CovoiturageResponseDTO;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.dto.VoitureDTO;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Participation;
import fr.ecoride.service.ICovoiturageService;
import fr.ecoride.service.IParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/covoiturages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CovoiturageController {

    private final ICovoiturageService covoiturageService;
    private final IParticipationService participationService;

    @GetMapping("/recherche")
    public ResponseEntity<List<CovoiturageResponseDTO>> rechercher(
            @RequestParam String depart,
            @RequestParam String arrivee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Covoiturage> resultats = covoiturageService.rechercher(depart, arrivee, date);
        return ResponseEntity.ok(
                resultats.stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/rechercherById")
    public ResponseEntity<CovoiturageResponseDTO> rechercherById(
            @RequestParam Long covoiturageId
    ) {
        Covoiturage resultat = covoiturageService.rechercherById(covoiturageId);
        return ResponseEntity.ok(mapToDTO(resultat));
    }


    @PostMapping("/creer")
    public ResponseEntity<?> publierTrajet(@RequestBody CovoiturageRequestDTO dto,
                                           @AuthenticationPrincipal UtilisateurDetails userDetails) {
        covoiturageService.creerTrajet(dto, userDetails.getUtilisateur());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mes-trajets")
    public ResponseEntity<?> getTrajetsConducteur(@AuthenticationPrincipal UtilisateurDetails userDetails) {
        List<Covoiturage> covoiturages = covoiturageService.getCovoituragesConducteur(userDetails.getUtilisateur());

        List<CovoiturageResponseDTO> dtos = covoiturages.stream()
                .map(CovoiturageResponseDTO::toDTO)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/mes-trajets-passager")
    public ResponseEntity<List<CovoiturageResponseDTO>> getCovoituragesEnTantQuePassager(@AuthenticationPrincipal UtilisateurDetails userDetails) {
        List<Participation> participations = participationService.findByPassager(userDetails.getUtilisateur());

        List<CovoiturageResponseDTO> dtoList = participations.stream()
                .map(Participation::getCovoiturage)
                .map(this::mapToDTO)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/annuler")
    public ResponseEntity<?> annulerTrajet(@RequestBody CovoiturageRequestDTO dto,
                                           @AuthenticationPrincipal UtilisateurDetails userDetails) {
        covoiturageService.annulerTrajet(dto, userDetails.getUtilisateur());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/demarrer")
    public ResponseEntity<?> demarrerCovoiturage(@RequestBody CovoiturageRequestDTO dto, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        covoiturageService.demarrerTrajet(dto, userDetails.getUtilisateur());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/terminer")
    public ResponseEntity<?> terminerCovoiturage(@RequestBody CovoiturageRequestDTO dto, @AuthenticationPrincipal UtilisateurDetails userDetails) {
        covoiturageService.terminerTrajet(dto, userDetails.getUtilisateur());
        return ResponseEntity.ok().build();
    }


    private CovoiturageResponseDTO mapToDTO(Covoiturage cov) {
        return CovoiturageResponseDTO.builder()
                .covoiturageId(cov.getCovoiturageId())
                .dateDepart(cov.getDateDepart().toString())
                .heureDepart(cov.getHeureDepart().toString())
                .dateArrivee(cov.getDateArrivee().toString())
                .heureArrivee(cov.getHeureArrivee().toString())
                .lieuDepart(cov.getLieuDepart())
                .lieuArrivee(cov.getLieuArrivee())
                .statut(cov.getStatut())
                .nbPlace(cov.getNbPlace())
                .prixPersonne(cov.getPrixPersonne())
                .conducteur(UtilisateurDTO.builder()
                        .utilisateurId(cov.getConducteur().getUtilisateurId())
                        .nom(cov.getConducteur().getNom())
                        .prenom(cov.getConducteur().getPrenom())
                        .email(cov.getConducteur().getEmail())
                        .pseudo(cov.getConducteur().getPseudo())
                        .photo(cov.getConducteur().getPhoto())
                        .suspendu(false)
                        .credit(cov.getConducteur().getCredit())
                        .build())
                .voiture(VoitureDTO.builder()
                        .voitureId(cov.getVoiture().getVoitureId())
                        .modele(cov.getVoiture().getModele())
                        .immatriculation(cov.getVoiture().getImmatriculation())
                        .energie(cov.getVoiture().getEnergie())
                        .couleur(cov.getVoiture().getCouleur())
                        .datePremiereImmatriculation(
                                cov.getVoiture().getDatePremiereImmatriculation() != null ?
                                        cov.getVoiture().getDatePremiereImmatriculation().toString() : null)
                        .marque(cov.getVoiture().getMarque().getLibelle())
                        .build())
                .build();
    }
}
