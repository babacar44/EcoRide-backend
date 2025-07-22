package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parametre")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Parametre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parametre_id")
    private Long parametreId;

    private String propriete;
    private String valeur;
}