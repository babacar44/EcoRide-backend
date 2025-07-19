package fr.ecoride.dto;

import fr.ecoride.model.Covoiturage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class CovoiturageResponseDTO implements Serializable {
    private Long covoiturageId;
    private String dateDepart;
    private String heureDepart;
    private String dateArrivee;
    private String heureArrivee;
    private String lieuDepart;
    private String lieuArrivee;
    private String statut;
    private int nbPlace;
    private BigDecimal prixPersonne;
    private UtilisateurDTO conducteur;
    private VoitureDTO voiture;

    public static CovoiturageResponseDTO toDTO(Covoiturage cov) {
        return CovoiturageResponseDTO.builder()
                .covoiturageId(cov.getCovoiturageId())
                .lieuDepart(cov.getLieuDepart())
                .lieuArrivee(cov.getLieuArrivee())
                .dateDepart(cov.getDateDepart().toString())
                .heureDepart(cov.getDateDepart().toString())
                .dateArrivee(cov.getDateArrivee().toString())
                .heureArrivee(cov.getHeureArrivee().toString())
                .prixPersonne(cov.getPrixPersonne())
                .nbPlace(cov.getNbPlace())
                .statut(cov.getStatut())
                .conducteur(UtilisateurDTO.builder()
                        .nom(cov.getConducteur().getNom())
                        .email(cov.getConducteur().getEmail())
                        .pseudo(cov.getConducteur().getPseudo())
                        .photo(cov.getConducteur().getPhoto())
                        .prenom(cov.getConducteur().getPrenom())
                        .utilisateurId(cov.getConducteur().getUtilisateurId())
                        .credit(cov.getConducteur().getCredit())
                        .telephone(cov.getConducteur().getTelephone())
                        .adresse(cov.getConducteur().getAdresse())
                        .suspendu(cov.getConducteur().isSuspendu())
                        .build())
                .voiture(
                        VoitureDTO.builder()
                                .immatriculation(cov.getVoiture().getImmatriculation())
                                .datePremiereImmatriculation(cov.getVoiture().getDatePremiereImmatriculation().toString())
                                .marque(cov.getVoiture().getMarque().getLibelle())
                                .modele(cov.getVoiture().getModele())
                                .couleur(cov.getVoiture().getCouleur())
                                .energie(cov.getVoiture().getEnergie())
                                .nbPlaces(cov.getVoiture().getNbPlaces())
                                .build()
                )
                .build();
    }

}
