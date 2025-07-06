package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoitureDTO {
    private Long voitureId;
    private String modele;
    private String immatriculation;
    private String energie;
    private String couleur;
    private String datePremiereImmatriculation;
    private String marque;
}