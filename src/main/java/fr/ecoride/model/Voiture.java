package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String datePremiereImmatriculation;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;
}