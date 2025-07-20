package fr.ecoride.service;

import fr.ecoride.dto.AvisRequestDTO;
import fr.ecoride.model.Utilisateur;

public interface IAvisService {

    void envoyerAvis(AvisRequestDTO dto, Utilisateur utilisateur);
}
