package fr.ecoride.service;

import fr.ecoride.dto.SignalementDTO;

import java.util.List;

public interface ISignalementService {

    List<SignalementDTO> getAllSignalementsMalPasses();

    void valider(SignalementDTO signalementDTO);
}
