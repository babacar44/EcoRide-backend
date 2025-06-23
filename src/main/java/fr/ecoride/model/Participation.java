package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean confirme;
    private boolean valide;

    @ManyToOne
    private Utilisateur passager;

    @ManyToOne
    private Covoiturage covoiturage;
}
