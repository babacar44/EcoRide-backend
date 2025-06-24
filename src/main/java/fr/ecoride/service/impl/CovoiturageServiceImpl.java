package fr.ecoride.service.impl;

import fr.ecoride.model.Covoiturage;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.service.ICovoiturageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CovoiturageServiceImpl implements ICovoiturageService {

    private final CovoiturageRepository covoiturageRepository;

    @Override
    public List<Covoiturage> rechercher(String lieuDepart, String lieuArrivee, LocalDate date) {
        return covoiturageRepository
                .findByLieuDepartIgnoreCaseAndLieuArriveeIgnoreCaseAndDateDepart(lieuDepart, lieuArrivee, date);
    }

    @Override
    public Covoiturage chercherProchainDisponible(String depart, String arrivee) {
        return null;
    }

    @Override
    public Covoiturage getById(Long id) {
        return covoiturageRepository.findById(id).orElse(null);
    }
}
