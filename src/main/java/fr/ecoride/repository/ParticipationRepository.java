package fr.ecoride.repository;

import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Participation;
import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByPassagerAndCovoiturage(Utilisateur passager, Covoiturage covoiturage);
    Participation findParticipationByPassagerAndCovoiturage(Utilisateur passager, Covoiturage covoiturage);
    List<Participation> findAllByPassager(Utilisateur utilisateur);
    List<Participation> findAllByCovoiturage(Covoiturage covoiturage);
    void deleteParticipationsByCovoiturage(Covoiturage covoiturage);
    void deleteParticipationByCovoiturage(Covoiturage covoiturage);
    Participation findParticipationByCovoiturageAndPassager(Covoiturage covoiturage, Utilisateur passager);
}
