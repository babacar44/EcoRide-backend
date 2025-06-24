package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    private String libelle;
}