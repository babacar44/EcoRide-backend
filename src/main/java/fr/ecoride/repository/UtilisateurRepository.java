package fr.ecoride.repository;

import fr.ecoride.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByPseudo(String pseudo);
}
