package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AvisResponseDTO implements Serializable {
    private Long id;
    private String numero;
    private UtilisateurDTO conducteur;
    private UtilisateurDTO passager;
    private String date;
    private String pseudo;
    private String email;
    private String commentaire;
    private Integer note;
    private String publierAvis;
}
