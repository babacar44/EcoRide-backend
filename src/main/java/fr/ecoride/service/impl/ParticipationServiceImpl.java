package fr.ecoride.service.impl;

import fr.ecoride.exception.BusinessException;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Participation;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.repository.ParticipationRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.service.IParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements IParticipationService {

    private final CovoiturageRepository covoiturageRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ParticipationRepository participationRepository;

    @Override
    public void participer(Long covoiturageId, Long utilisateurId) {
        Covoiturage covoiturage = covoiturageRepository.findById(covoiturageId)
                .orElseThrow(() -> new NotFoundException("Covoiturage non trouvé"));
        Utilisateur passager = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        boolean dejaParticipe = participationRepository.existsByPassagerAndCovoiturage(passager, covoiturage);
        if (dejaParticipe) {
            throw new BusinessException("Déjà inscrit à ce covoiturage");
        }

        if (covoiturage.getNbPlace() <= 0) {
            throw new BusinessException("Plus de place disponible");
        }

        Participation participation = Participation.builder()
                .passager(passager)
                .covoiturage(covoiturage)
                .confirme(true)
                .valide(false)
                .build();
        participationRepository.save(participation);
        passager.setCredit(passager.getCredit().subtract(covoiturage.getPrixPersonne()));
        utilisateurRepository.save(passager);
        covoiturage.setNbPlace(covoiturage.getNbPlace() - 1);
        covoiturageRepository.save(covoiturage);
    }
}

