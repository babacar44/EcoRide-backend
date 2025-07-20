package fr.ecoride.repository;

import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {

    List<Covoiturage> findByLieuDepartIgnoreCaseAndLieuArriveeIgnoreCaseAndDateDepartAndStatut(String lieuDepart, String lieuArrivee, LocalDate date, String statut);
    List<Covoiturage> findAllByConducteur(Utilisateur utilisateur);
    Covoiturage findByCovoiturageId(Long covoiturageId);

    @Query("""
          SELECT NEW map(c.dateDepart AS date, COUNT(c) AS total)
          FROM Covoiturage c
          WHERE c.statut = 'FERME'
          GROUP BY c.dateDepart
          ORDER BY c.dateDepart ASC
    """)
    List<Map<String, Object>> getCovoituragesTerminesParJour();

    @Query("""
          SELECT NEW map(c.dateDepart AS date, COUNT(c) AS total)
          FROM Covoiturage c
          GROUP BY c.dateDepart
          ORDER BY c.dateDepart ASC
    """)
    List<Map<String, Object>> getCovoituragesParJour();


}
