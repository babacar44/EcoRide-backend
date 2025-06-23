package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pseudo;
    private String email;
    private String motDePasse;
    private int credit;
    private boolean suspendu;

    private String role;

    @OneToMany(mappedBy = "utilisateur")
    private List<Vehicule> vehicules;

    @OneToMany(mappedBy = "passager")
    private List<Participation> participations;

    @OneToMany(mappedBy = "chauffeur")
    private List<Covoiturage> covoiturages;
}
