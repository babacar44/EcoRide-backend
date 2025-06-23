package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Covoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresseDepart;
    private String adresseArrivee;
    private LocalDateTime dateHeureDepart;
    private LocalDateTime dateHeureArrivee;

    private int nbPlaces;
    private double prix;

    private boolean demarre;
    private boolean termine;

    @ManyToOne
    private Utilisateur chauffeur;

    @ManyToOne
    private Vehicule vehicule;

    @OneToMany(mappedBy = "covoiturage")
    private List<Participation> participations;
}
