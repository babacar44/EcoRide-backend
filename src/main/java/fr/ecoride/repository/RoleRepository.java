package fr.ecoride.repository;

import fr.ecoride.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByLibelle(String libelle);
}
