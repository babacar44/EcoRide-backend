package fr.ecoride.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "configuration")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuration")
    private Long idConfiguration;
}