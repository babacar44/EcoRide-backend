package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utilisateur")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilisateur_id")
    private Long utilisateurId;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    private String photo;
    private String pseudo;
    @Column(precision = 10, scale = 2)
    private BigDecimal credit;
    private boolean suspendu;

    @ManyToMany
    @JoinTable(name = "utilisateur_role",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}