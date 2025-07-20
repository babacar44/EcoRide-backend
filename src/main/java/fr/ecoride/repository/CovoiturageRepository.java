package fr.ecoride.repository;

import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {

    List<Covoiturage> findByLieuDepartIgnoreCaseAndLieuArriveeIgnoreCaseAndDateDepartAndStatut(String lieuDepart, String lieuArrivee, LocalDate date, String statut);
    List<Covoiturage> findAllByConducteur(Utilisateur utilisateur);
    Covoiturage findByCovoiturageId(Long covoiturageId);

}
