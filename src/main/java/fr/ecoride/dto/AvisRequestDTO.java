package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AvisRequestDTO implements Serializable {
    private Long covoiturageId;
    private Boolean statut;
    private String commentaire;
    private Integer note;
}
