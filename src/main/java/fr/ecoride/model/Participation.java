package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean confirme;
    private boolean valide;
    @Column(name = "avis_statut")
    private boolean avisStatut;
    @Column(name = "avis_commentaire", columnDefinition = "TEXT")
    private String avisCommentaire;
    @Column(name = "avis_note")
    private int avisNote;
    @Column(name = "avis_valide")
    private boolean avisValide;
    @Column(name = "avis_date")
    private LocalDate avisDate;

    @ManyToOne
    @JoinColumn(name = "passager_id")
    private Utilisateur passager;

    @ManyToOne
    @JoinColumn(name = "covoiturage_id")
    private Covoiturage covoiturage;

}
