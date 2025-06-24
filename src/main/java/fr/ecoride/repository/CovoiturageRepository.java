package fr.ecoride.repository;

import fr.ecoride.model.Covoiturage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

import java.util.List;

public interface CovoiturageRepository extends JpaRepository<Covoiturage, Long> {

    List<Covoiturage> findByLieuDepartIgnoreCaseAndLieuArriveeIgnoreCaseAndDateDepart(String lieuDepart, String lieuArrivee, LocalDate date);

}
