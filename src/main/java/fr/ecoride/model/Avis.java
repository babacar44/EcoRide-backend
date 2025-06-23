package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int note;
    private String commentaire;
    private boolean valide;

    @ManyToOne
    private Utilisateur auteur;

    @ManyToOne
    private Utilisateur conducteur;
}
