package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilisateurDTO {
    private Long utilisateurId;
    private String nom;
    private String prenom;
    private String email;
    private String pseudo;
    private String photo;
    private Boolean suspendu;
    private Integer credit;
}
