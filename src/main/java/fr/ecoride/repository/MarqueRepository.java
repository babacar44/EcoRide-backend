package fr.ecoride.repository;

import fr.ecoride.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarqueRepository extends JpaRepository<Marque, Long> {
    Optional<Marque> findByLibelleIgnoreCase(String marque);
}
