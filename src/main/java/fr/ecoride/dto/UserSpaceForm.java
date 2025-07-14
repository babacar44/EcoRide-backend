package fr.ecoride.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserSpaceForm implements Serializable {

    private String role;
    private String roleEcoride;

    private List<VoitureForm> voitures;
    private PreferenceForm preferences;

    @Data
    public static class VoitureForm {
        private String immatriculation;
        private String datePremiereImmatriculation;
        private String marque;
        private String modele;
        private String couleur;
        private String energie;
        private int nbPlaces;
    }

    @Data
    public static class PreferenceForm {
        private boolean fumeur;
        private boolean animaux;
        private String autres;
    }
}
