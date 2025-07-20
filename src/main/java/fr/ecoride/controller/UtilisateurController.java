package fr.ecoride.controller;

import fr.ecoride.config.security.UtilisateurDetails;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.exception.BusinessException;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.mapper.UtilisateurMapper;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.RoleRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.service.IMailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static fr.ecoride.mapper.UtilisateurMapper.toDTO;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final IMailService mailService;

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

    @PostMapping("/creer-employes")
    @Transactional
    public void createEmployes(@RequestBody UtilisateurDTO request) {
        var user = utilisateurRepository.findByEmail(request.getEmail());
        if (user.isPresent()){
            throw new BusinessException("Un utilisateur avec cet email existe deja");
        }
        var user2 = utilisateurRepository.findByPseudo(request.getPseudo());
        if (user2.isPresent()){
            throw new BusinessException("Un utilisateur avec cet pseudo existe deja");
        }
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(passwordEncoder.encode("passer1234"))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .dateNaissance(LocalDate.parse(request.getDateNaissance()))
                .pseudo(request.getPseudo())
                .roles(List.of(roleRepository.findByLibelle("EMPLOYE")))
                .build();
        utilisateurRepository.save(utilisateur);
        mailService.envoyerEmail(utilisateur.getEmail(), "Compte Collaborateur Ecoride Cree", "Bonjour, merci de vous connecter sur la plateforme Ecoride avec votre nouveau compte, email : "+ utilisateur.getEmail()+
                " et mot de passe: passer1234");
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
