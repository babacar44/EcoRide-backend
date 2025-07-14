package fr.ecoride.controller;

import fr.ecoride.config.security.JwtService;
import fr.ecoride.dto.AuthRequestDTO;
import fr.ecoride.dto.AuthResponseDTO;
import fr.ecoride.dto.RegisterRequestDTO;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.RoleRepository;
import fr.ecoride.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthController(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .dateNaissance(LocalDate.parse(request.getDateNaissance()))
                .pseudo(request.getPseudo())
                .credit(new BigDecimal(20))
                .roles(List.of(roleRepository.findByLibelle("USER")))
                .build();
        utilisateurRepository.save(utilisateur);

        String token = jwtService.generateToken(new fr.ecoride.config.security.UtilisateurDetails(utilisateur));
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDTO("Les identifiants sont erronés"));
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtService.generateToken(new fr.ecoride.config.security.UtilisateurDetails(utilisateur));
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
