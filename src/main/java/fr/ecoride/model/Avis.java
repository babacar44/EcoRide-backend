package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avis")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avis_id")
    private Long avisId;

    private String commentaire;
    private String note;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur auteur;
}