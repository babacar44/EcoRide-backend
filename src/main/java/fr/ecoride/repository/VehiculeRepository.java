package fr.ecoride.repository;

import fr.ecoride.model.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Voiture, Long> {
}
