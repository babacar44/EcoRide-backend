package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.mapper.UtilisateurMapper;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static fr.ecoride.mapper.UtilisateurMapper.*;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<UtilisateurDTO> getAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public UtilisateurDTO getById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + id));
        return toDTO(utilisateur);
    }

    @GetMapping("/utilisateur/infos")
    public UtilisateurDTO getByInfoUser(@AuthenticationPrincipal UtilisateurDetails userDetails) {
        Utilisateur utilisateur = utilisateurRepository.findById(userDetails.getUtilisateur().getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + userDetails.getUtilisateur().getUtilisateurId()));
        return toDTO(utilisateur);
    }

    @PostMapping
    public UtilisateurDTO create(@RequestBody UtilisateurDTO dto) {
        Utilisateur entity = toEntity(dto);
        return toDTO(utilisateurRepository.save(entity));
    }

    @PutMapping("/{id}")
    public UtilisateurDTO update(@PathVariable Long id, @RequestBody UtilisateurDTO dto) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + id));

        utilisateur.setNom(dto.getNom());
        utilisateur.setPrenom(dto.getPrenom());
        utilisateur.setEmail(dto.getEmail());
        utilisateur.setTelephone(dto.getTelephone());
        utilisateur.setAdresse(dto.getAdresse());
        utilisateur.setDateNaissance(LocalDate.parse(dto.getDateNaissance()));
        utilisateur.setPhoto(dto.getPhoto());
        utilisateur.setPseudo(dto.getPseudo());
        utilisateur.setSuspendu(dto.isSuspendu());
        utilisateur.setCredit(dto.getCredit());

        return toDTO(utilisateurRepository.save(utilisateur));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + id));
        utilisateurRepository.delete(utilisateur);
        return ResponseEntity.noContent().build();
    }
}
