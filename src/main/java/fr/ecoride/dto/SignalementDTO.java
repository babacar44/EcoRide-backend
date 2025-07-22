package fr.ecoride.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @Builder
public class SignalementDTO implements Serializable {
    private Long covoiturageId;
    private Long participationId;
    private String numero;

    private UtilisateurDTO conducteur;
    private UtilisateurDTO passager;

    private String lieuDepart;
    private String lieuArrivee;
    private String dateDepart;

    private String commentaire;
    private Integer note;
}
