package fr.ecoride.controller;

import fr.ecoride.config.security.JwtService;
import fr.ecoride.dto.AuthRequestDto;
import fr.ecoride.dto.AuthResponseDto;
import fr.ecoride.dto.RegisterRequestDto;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.model.Role;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.RoleRepository;
import fr.ecoride.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto request) {
        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .dateNaissance(request.getDateNaissance())
                .pseudo(request.getPseudo())
                .credit(20)
                .roles(List.of(roleRepository.findByLibelle("USER")))
                .build();
        utilisateurRepository.save(utilisateur);

        String token = jwtService.generateToken(new fr.ecoride.config.security.UtilisateurDetails(utilisateur));
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponseDto("Les identifiants sont erronés"));
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtService.generateToken(new fr.ecoride.config.security.UtilisateurDetails(utilisateur));
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
