package fr.ecoride.service;

import fr.ecoride.dto.AvisRequestDTO;
import fr.ecoride.dto.AvisResponseDTO;
import fr.ecoride.model.Utilisateur;

import java.util.List;

public interface IAvisService {

    void envoyerAvis(AvisRequestDTO dto, Utilisateur utilisateur);

    List<AvisResponseDTO> getAvisEnAttente();
}
