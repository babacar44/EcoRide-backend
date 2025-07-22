package fr.ecoride.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSpaceResponseDTO {
    private String role;
    private String roleEcoride;
    private List<VoitureDTO> voitures;
    private PreferenceDTO preferences;

    @Data
    @Builder
    public static class PreferenceDTO {
        private boolean fumeur;
        private boolean animaux;
        private String autres;
    }
}
