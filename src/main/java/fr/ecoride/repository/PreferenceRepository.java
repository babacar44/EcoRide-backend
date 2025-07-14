package fr.ecoride.repository;

import fr.ecoride.model.Preference;
import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    void deleteByUtilisateur(Utilisateur utilisateur);

    List<Preference> findByUtilisateur(Utilisateur utilisateur);
}
