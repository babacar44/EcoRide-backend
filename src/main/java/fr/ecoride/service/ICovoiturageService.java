package fr.ecoride.service;

import fr.ecoride.dto.CovoiturageRequestDTO;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public interface ICovoiturageService {

    List<Covoiturage> rechercher(String lieuDepart, String lieuArrivee, LocalDate date);

    Covoiturage chercherProchainDisponible(String depart, String arrivee);

    Covoiturage getById(Long id);

    void creerTrajet(CovoiturageRequestDTO dto, Utilisateur utilisateur);

    List<Covoiturage> getCovoituragesConducteur(Utilisateur utilisateur);

    void annulerTrajet(CovoiturageRequestDTO dto, Utilisateur utilisateur);

    void demarrerTrajet(CovoiturageRequestDTO dto, Utilisateur user);

    void terminerTrajet(CovoiturageRequestDTO dto, Utilisateur user);

    Covoiturage rechercherById(Long covoiturageId);
}
