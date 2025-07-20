package fr.ecoride.mapper;

import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.model.Role;
import fr.ecoride.model.Utilisateur;

import java.time.LocalDate;
import java.util.List;

public class UtilisateurMapper {

    public static UtilisateurDTO toDTO(Utilisateur u) {
        return UtilisateurDTO.builder()
                .utilisateurId(u.getUtilisateurId())
                .nom(u.getNom())
                .prenom(u.getPrenom())
                .email(u.getEmail())
                .role(u.getRoles().getFirst().getLibelle())
                .telephone(u.getTelephone())
                .adresse(u.getAdresse())
                .dateNaissance(u.getDateNaissance().toString())
                .photo(u.getPhoto())
                .pseudo(u.getPseudo())
                .suspendu(u.isSuspendu())
                .credit(u.getCredit())
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDTO dto) {
        return Utilisateur.builder()
                .utilisateurId(dto.getUtilisateurId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .roles(List.of(new Role(dto.getRole())))
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .dateNaissance(LocalDate.parse(dto.getDateNaissance()))
                .photo(dto.getPhoto())
                .pseudo(dto.getPseudo())
                .suspendu(dto.isSuspendu())
                .credit(dto.getCredit())
                .build();
    }
}
