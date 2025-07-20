package fr.ecoride.controller;

import fr.ecoride.dto.AvisResponseDTO;
import fr.ecoride.dto.SignalementDTO;
import fr.ecoride.service.IAvisService;
import fr.ecoride.service.ISignalementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employe/signalements")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SignalementController {

    private final ISignalementService signalementService;
    private final IAvisService avisService;

    @GetMapping
    public ResponseEntity<List<SignalementDTO>> signalements() {
        List<SignalementDTO> result = signalementService.getAllSignalementsMalPasses();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/valider")
    public ResponseEntity<List<SignalementDTO>> valider(@RequestBody SignalementDTO signalementDTO) {
        signalementService.valider(signalementDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avis-attente")
    public ResponseEntity<List<AvisResponseDTO>> getAvisEnAttente(){
        return ResponseEntity.ok(avisService.getAvisEnAttente());
    }

    @PostMapping("/avis/{id}/valider")
    public ResponseEntity<List<SignalementDTO>> validerAvis(@PathVariable Long id) {
        signalementService.validerAvis(id);
        return ResponseEntity.ok().build();
    }
}
