package fr.ecoride.service.impl;

import fr.ecoride.dto.AvisRequestDTO;
import fr.ecoride.dto.AvisResponseDTO;
import fr.ecoride.dto.UtilisateurDTO;
import fr.ecoride.exception.BusinessException;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.repository.CovoiturageRepository;
import fr.ecoride.repository.ParticipationRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.service.IAvisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvisServiceImpl implements IAvisService {

    private final CovoiturageRepository covoiturageRepository;
    private final ParticipationRepository participationRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional
    public void envoyerAvis(AvisRequestDTO dto, Utilisateur utilisateur) {
        var covoiturage = covoiturageRepository.findByCovoiturageId(dto.getCovoiturageId());
        if (covoiturage == null) {
            throw new NotFoundException("Erreur Technique");
        }
        var participation = participationRepository.findParticipationByCovoiturageAndPassager(covoiturage, utilisateur);
        if (participation == null) {
            throw new NotFoundException("Erreur Technique");
        }
        if (participation.getAvisDate() != null) {
            throw new BusinessException("Vous avez envoyer un avis");
        }
        participation.setAvisCommentaire(dto.getCommentaire());
        participation.setAvisNote(dto.getNote());
        participation.setAvisDate(LocalDate.now());
        participation.setAvisStatut(dto.getStatut());
        participation.setValide(dto.getStatut());

        participationRepository.save(participation);
        var participations =  participationRepository.findAllByCovoiturage(covoiturage);
        var participationsKO = participations
                .stream()
                .filter(participation1 -> !participation1.isAvisStatut())
                .toList();
        if (participationsKO.isEmpty()) {
          var prixPersonne =  covoiturage.getPrixPersonne();
          var user = covoiturage.getConducteur();
          user.setCredit(user.getCredit().add(prixPersonne.multiply(BigDecimal.valueOf(participations.size()))));
          utilisateurRepository.save(user);
        }
        var participationsNotees = participations.
                stream()
                        .filter(participation1 -> participation1.getAvisDate() != null)
                                .toList();
        if (participationsNotees.size() == participations.size()) {
            covoiturage.setStatut("FERME");
            covoiturageRepository.save(covoiturage);
        }
    }

    @Override
    public List<AvisResponseDTO> getAvisEnAttente() {
       var participations =  participationRepository.findParticipationsByPublierAvisIsNull();

       if (participations.isEmpty()) {
           return List.of();
       }
        return participations
                .stream()
                .map(participation -> {
                    var conducteur = participation.getCovoiturage().getConducteur();
                    var passager = participation.getPassager();
                    return AvisResponseDTO
                            .builder()
                            .publierAvis(participation.getPublierAvis())
                            .date(participation.getAvisDate().toString())
                            .note(participation.getAvisNote())
                            .commentaire(participation.getAvisCommentaire())
                            .conducteur(UtilisateurDTO
                                    .builder()
                                    .pseudo(conducteur.getPseudo())
                                    .email(conducteur.getEmail())
                                    .build()
                            )
                            .passager(UtilisateurDTO.builder()
                                    .pseudo(passager.getPseudo())
                                    .email(passager.getEmail())
                                    .build())
                            .id(participation.getId())
                            .build();
                }).toList();
    }
}
