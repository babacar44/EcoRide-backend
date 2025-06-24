package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CovoiturageResponseDTO {
    private Long covoiturageId;
    private String dateDepart;
    private String heureDepart;
    private String dateArrivee;
    private String heureArrivee;
    private String lieuDepart;
    private String lieuArrivee;
    private String statut;
    private int nbPlace;
    private double prixPersonne;
    private UtilisateurDTO conducteur;
    private VoitureDTO voiture;
}
