package fr.ecoride.service;

import fr.ecoride.model.Participation;
import fr.ecoride.model.Utilisateur;

import java.util.List;

public interface IParticipationService {

    void participer(Long covoiturageId, Long utilisateurId);

    List<Participation> findByPassager(Utilisateur utilisateur);

    void quitter(Long covoiturageId, Long utilisateurId);
}
