package fr.ecoride.controller;

import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CovoiturageRepository covoiturageRepository;
    private final UtilisateurRepository utilisateurRepository;

    @GetMapping("/stats/credits-par-jour")
    public ResponseEntity<List<Map<String, Object>>> getCreditsParJour() {
        List<Map<String, Object>> covoituragesParJour = covoiturageRepository.getCovoituragesTerminesParJour();

        List<Map<String, Object>> result = covoituragesParJour.stream()
                .map(entry -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", entry.get("date"));
                    map.put("credits", ((Long) entry.get("total")) * 2);
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/stats/covoiturages-par-jour")
    public ResponseEntity<List<Map<String, Object>>> getCovoituragesParJour() {
        List<Map<String, Object>> data = covoiturageRepository.getCovoituragesParJour();
        return ResponseEntity.ok(data);
    }

    @PutMapping("/utilisateurs/{id}/suspension")
    public void suspendreUser(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + id));
        utilisateur.setSuspendu(!utilisateur.isSuspendu());
        utilisateurRepository.save(utilisateur);
    }

}
