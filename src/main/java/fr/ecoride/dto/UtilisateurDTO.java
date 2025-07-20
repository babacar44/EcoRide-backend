package fr.ecoride.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UtilisateurDTO implements Serializable {
    private Long utilisateurId;
    private String nom;
    private String prenom;
    private String role;
    private String email;
    private String telephone;
    private String adresse;
    private String dateNaissance;
    private String photo;
    private String pseudo;
    private boolean suspendu;
    private BigDecimal credit;
}
