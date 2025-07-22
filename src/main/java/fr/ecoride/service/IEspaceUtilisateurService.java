package fr.ecoride.service;

import fr.ecoride.dto.UserSpaceForm;
import fr.ecoride.dto.UserSpaceResponseDTO;
import fr.ecoride.dto.VoitureDTO;
import fr.ecoride.model.Utilisateur;

public interface IEspaceUtilisateurService {

    void enregistrer(UserSpaceForm form, String email);

    UserSpaceResponseDTO getInfos(Utilisateur user);

    Boolean supprimerVoiture(VoitureDTO voitureDTO, Utilisateur utilisateur);
}
