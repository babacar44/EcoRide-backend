package fr.ecoride.service.impl;

import fr.ecoride.dto.SignalementDTO;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.exception.BusinessException;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Covoiturage;
import fr.ecoride.model.Participation;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.AvisRepository;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.repository.ParticipationRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.service.ISignalementService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignalementServiceImpl implements ISignalementService {

    private final AvisRepository avisRepository;
    private final ParticipationRepository participationRepository;
    private final CovoiturageRepository covoiturageRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public List<SignalementDTO> getAllSignalementsMalPasses() {
        var participationsSignales = participationRepository.findAllByValideIsFalseAndAvisStatutIsFalseAndAvisDateNotNull();
        return participationsSignales.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional
    public void valider(SignalementDTO signalementDTO) {
       var covoiturage =  covoiturageRepository.findById(signalementDTO.getCovoiturageId());
       if (covoiturage.isEmpty()){
           throw new NotFoundException("Covoiturage not found");
       }
        var participations =  participationRepository.findAllByCovoiturage(covoiturage.get());
        var participationsNonNotees = participations.
                stream()
                .filter(participation1 -> participation1.getAvisDate() == null)
                .toList();
        if (!participationsNonNotees.isEmpty()){
            throw new BusinessException("En attente que tous les passagers notent le trajet");
        }
       var participation =  participationRepository.findParticipationByIdAndCovoiturage(signalementDTO.getParticipationId(), covoiturage.get());
       participation.setValide(true);
       participationRepository.save(participation);

        var participationsInvalide = participations.
                stream()
                .filter(participation1 -> !participation1.isValide())
                .toList();
        if (participationsInvalide.isEmpty()){
            var prixPersonne =  covoiturage.get().getPrixPersonne();
            var user = covoiturage.get().getConducteur();
            user.setCredit(user.getCredit().add(prixPersonne.multiply(BigDecimal.valueOf(participations.size()))));
            utilisateurRepository.save(user);
        }
    }

    private SignalementDTO mapToDTO(Participation participation) {
        Utilisateur passager = participation.getPassager();
        Covoiturage cov = participation.getCovoiturage();
        Utilisateur conducteur = cov.getConducteur();

        return SignalementDTO.builder()
                .covoiturageId(cov.getCovoiturageId())
                .lieuDepart(cov.getLieuDepart())
                .lieuArrivee(cov.getLieuArrivee())
                .dateDepart(cov.getDateDepart().toString())
                .commentaire(participation.getAvisCommentaire())
                .conducteur(mapUtilisateur(conducteur))
                .passager(mapUtilisateur(passager))
                .numero("TRJ-" + cov.getCovoiturageId() + cov.getDateDepart())
                .note(participation.getAvisNote())
                .participationId(participation.getId())
                .build();
    }

    private UtilisateurDTO mapUtilisateur(Utilisateur u) {
        return UtilisateurDTO.builder()
                .nom(u.getNom())
                .prenom(u.getPrenom())
                .telephone(u.getTelephone())
                .email(u.getEmail())
                .credit(u.getCredit())
                .pseudo(u.getPseudo())
                .build();
    }
}
