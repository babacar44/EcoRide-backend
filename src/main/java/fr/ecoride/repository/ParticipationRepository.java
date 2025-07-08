package fr.ecoride.repository;

import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Participation;
import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByPassagerAndCovoiturage(Utilisateur passager, Covoiturage covoiturage);
}
