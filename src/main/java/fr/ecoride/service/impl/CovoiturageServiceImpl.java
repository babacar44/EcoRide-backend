package fr.ecoride.service.impl;

import fr.ecoride.dto.CovoiturageRequestDTO;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.model.Voiture;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.repository.ParticipationRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.repository.VoitureRepository;
import fr.ecoride.service.ICovoiturageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CovoiturageServiceImpl implements ICovoiturageService {

    private final CovoiturageRepository covoiturageRepository;
    private final VoitureRepository voitureRepository;
    private final ParticipationRepository participationRepository;
    private final UtilisateurRepository utilisateurRepository;

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

    @Override
    @Transactional
    public void creerTrajet(CovoiturageRequestDTO dto, Utilisateur utilisateur) {
        Voiture voiture = voitureRepository.findByImmatriculation(dto.getVoiture().getImmatriculation())
                .orElseThrow(() -> new NotFoundException("Véhicule introuvable"));

        LocalDate dateDep = LocalDate.parse(dto.getDateDepart());
        LocalDate dateArr = LocalDate.parse(dto.getDateArrivee());
        LocalTime heureDep = LocalTime.parse(dto.getHeureDepart());
        LocalTime heureArr = LocalTime.parse(dto.getHeureArrivee());

        LocalDateTime dateHeureDep = LocalDateTime.of(dateDep, heureDep);
        LocalDateTime dateHeureArr = LocalDateTime.of(dateArr, heureArr);

        Covoiturage covoiturage = Covoiturage.builder()
                .lieuDepart(dto.getLieuDepart())
                .lieuArrivee(dto.getLieuArrivee())
                .dateArrivee(dateArr)
                .dateDepart(dateDep)
                .heureDepart(LocalTime.from(dateHeureDep))
                .heureArrivee(LocalTime.from(dateHeureArr))
                .nbPlace(voiture.getNbPlaces())
                .prixPersonne(dto.getPrixPersonne())
                .statut("OUVERT")
                .conducteur(utilisateur)
                .voiture(voiture)
                .build();

        covoiturageRepository.save(covoiturage);
    }

    @Override
    public List<Covoiturage> getCovoituragesConducteur(Utilisateur utilisateur) {
        return covoiturageRepository.findAllByConducteur(utilisateur);
    }

    @Override
    @Transactional
    public void annulerTrajet(CovoiturageRequestDTO dto, Utilisateur utilisateur) {
        Voiture voiture = voitureRepository.findByImmatriculation(dto.getVoiture().getImmatriculation())
                .orElseThrow(() -> new NotFoundException("Véhicule introuvable"));

        Covoiturage covoiturage = covoiturageRepository.findByCovoiturageId(dto.getCovoiturageId());
        if (covoiturage == null) {
            covoiturage.setNbPlace(voiture.getNbPlaces());
            covoiturage.setStatut("ANNULE");
        }
        participationRepository.findAllByCovoiturage(covoiturage).forEach(participation -> {
            var user = participation.getPassager();
            user.setCredit(covoiturage.getPrixPersonne().add(participation.getPassager().getCredit()));
            utilisateurRepository.save(user);
        });
        participationRepository.deleteParticipationsByCovoiturage(covoiturage);
        covoiturageRepository.save(covoiturage);
    }

}
