package fr.ecoride.controller;

import fr.ecoride.dto.CovoiturageResponseDTO;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.dto.VoitureDTO;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.service.ICovoiturageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Covoiturages", description = "API des covoiturages")
@RestController
@RequestMapping("/api/covoiturages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CovoiturageController {

    private final ICovoiturageService covoiturageService;

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
                        .suspendu(false) // à adapter si le champ existe
                        .credit(cov.getConducteur().getCredit())    // à adapter selon les besoins
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
