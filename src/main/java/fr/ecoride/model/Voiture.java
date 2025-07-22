package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "voiture")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voiture_id")
    private Long voitureId;
    private String modele;
    private String immatriculation;
    private String energie;
    private String couleur;
    private LocalDate datePremiereImmatriculation;
    @Column(name = "nb_places")
    private int nbPlaces;
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
}