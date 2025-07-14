package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class VoitureDTO implements Serializable {
    private Long voitureId;
    private String immatriculation;
    private String datePremiereImmatriculation;
    private String marque;
    private String modele;
    private String couleur;
    private String energie;
    private int nbPlaces;
}