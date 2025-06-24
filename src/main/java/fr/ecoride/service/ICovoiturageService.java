package fr.ecoride.service;

import fr.ecoride.model.Covoiturage;

import java.time.LocalDate;
import java.util.List;

public interface ICovoiturageService {

    List<Covoiturage> rechercher(String lieuDepart, String lieuArrivee, LocalDate date);

    Covoiturage chercherProchainDisponible(String depart, String arrivee);

    Covoiturage getById(Long id);
}
