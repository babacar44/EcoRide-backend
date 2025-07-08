package fr.ecoride.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UtilisateurDTO {
    private Long utilisateurId;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String dateNaissance;
    private String photo;
    private String pseudo;
    private boolean suspendu;
    private BigDecimal credit;
}
