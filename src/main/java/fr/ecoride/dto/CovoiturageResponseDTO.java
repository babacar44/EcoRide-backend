package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class CovoiturageResponseDTO implements Serializable {
    private Long covoiturageId;
    private String dateDepart;
    private String heureDepart;
    private String dateArrivee;
    private String heureArrivee;
    private String lieuDepart;
    private String lieuArrivee;
    private String statut;
    private int nbPlace;
    private BigDecimal prixPersonne;
    private UtilisateurDTO conducteur;
    private VoitureDTO voiture;
}
