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

    @Column(name = "commentaire", columnDefinition = "TEXT")
    private String commentaire;
    private int note;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private Utilisateur auteur;

    @ManyToOne
    @JoinColumn(name = "destinataire_id")
    private Utilisateur destinataire;
}