package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "covoiturage")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Covoiturage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "covoiturage_id")
    private Long covoiturageId;

    private LocalDate dateDepart;
    private LocalTime heureDepart;
    private String lieuDepart;

    private LocalDate dateArrivee;
    private LocalTime heureArrivee;
    private String lieuArrivee;

    private String statut;
    private int nbPlace;
    private float prixPersonne;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur conducteur;

    @ManyToOne
    @JoinColumn(name = "voiture_id")
    private Voiture voiture;
}