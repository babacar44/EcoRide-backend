package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marque")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marque_id")
    private Long marqueId;

    private String libelle;
}