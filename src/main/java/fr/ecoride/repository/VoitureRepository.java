package fr.ecoride.repository;

import fr.ecoride.model.Utilisateur;
import fr.ecoride.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    Optional<Voiture> findFirstByUtilisateur(Utilisateur utilisateur);

    List<Voiture> findAllByUtilisateur(Utilisateur utilisateur1);

    Optional<Voiture> findVoitureByImmatriculationIgnoreCase(String immatriculation);

    void deleteVoitureByImmatriculationIgnoreCaseAndUtilisateur_UtilisateurId(String immatriculation, Long utilisateurId);

    Optional<Voiture>findByImmatriculation(String immatriculation);
}
