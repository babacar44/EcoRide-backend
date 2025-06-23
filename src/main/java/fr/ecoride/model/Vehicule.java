package fr.ecoride.model;

import fr.ecoride.enumeration.Energie;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plaque;
    private String modele;
    private String marque;
    private String couleur;

    @Enumerated(EnumType.STRING)
    private Energie energie;

    @ManyToOne
    private Utilisateur utilisateur;
}
