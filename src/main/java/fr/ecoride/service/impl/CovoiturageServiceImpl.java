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
import fr.ecoride.service.IMailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CovoiturageServiceImpl implements ICovoiturageService {

    private final CovoiturageRepository covoiturageRepository;
    private final VoitureRepository voitureRepository;
    private final ParticipationRepository participationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final IMailService mailService;

    @Override
    public List<Covoiturage> rechercher(String lieuDepart, String lieuArrivee, LocalDate date) {
        return covoiturageRepository
                .findByLieuDepartIgnoreCaseAndLieuArriveeIgnoreCaseAndDateDepartAndStatut(lieuDepart, lieuArrivee, date, "OUVERT");
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
        Covoiturage covoiturage = covoiturageRepository.findByCovoiturageId(dto.getCovoiturageId());
        if (covoiturage == null) {
            throw new NotFoundException("Covoiturage introuvable");
        }
        participationRepository.findAllByCovoiturage(covoiturage).forEach(participation -> {
            var user = participation.getPassager();
            user.setCredit(covoiturage.getPrixPersonne().add(participation.getPassager().getCredit()));
            utilisateurRepository.save(user);
            participationRepository.deleteParticipationByCovoiturage(covoiturage);
            mailService.envoyerEmail(
                    user.getEmail(),
                    "Annulation du covoiturage",
                    "Le chauffeur " + utilisateur.getPseudo() + " a annulé le covoiturage: "+ dto.getLieuDepart() + " -> " + dto.getLieuArrivee() + " du " + dto.getDateDepart() + " à " + covoiturage.getHeureDepart()
            );
        });
        covoiturage.setStatut("ANNULE");
        covoiturageRepository.save(covoiturage);
    }

    @Override
    @Transactional
    public void demarrerTrajet(CovoiturageRequestDTO dto, Utilisateur user) {
        Covoiturage covoiturage = covoiturageRepository.findByCovoiturageId(dto.getCovoiturageId());
        if (covoiturage == null) {
            throw new NotFoundException("Covoiturage introuvable");
        }
        covoiturage.setStatut("EN_COURS");
        covoiturageRepository.save(covoiturage);
    }

    @Override
    @Transactional
    public void terminerTrajet(CovoiturageRequestDTO dto, Utilisateur chauffeur) {
        Covoiturage covoiturage = covoiturageRepository.findByCovoiturageId(dto.getCovoiturageId());
        if (covoiturage == null) {
            throw new NotFoundException("Covoiturage introuvable");
        }
        participationRepository.findAllByCovoiturage(covoiturage).forEach(participation -> {
            var user = participation.getPassager();
            String contenu = genererContenuMailConfirmation(
                    user.getPrenom(),
                    covoiturage.getLieuDepart(),
                    covoiturage.getLieuArrivee(),
                    covoiturage.getDateDepart(),
                    covoiturage.getConducteur().getPrenom(),
                    covoiturage.getConducteur().getNom()
            );
            mailService.envoyerEmail(
                    user.getEmail(),
                    "Confirmez votre EcoRide",
                    contenu
            );
        });
        covoiturage.setStatut("TERMINE");
        covoiturageRepository.save(covoiturage);
    }

    @Override
    public Covoiturage rechercherById(Long covoiturageId) {
        return covoiturageRepository.findByCovoiturageId(covoiturageId);
    }

    public String genererContenuMailConfirmation(String prenomPassager, String lieuDepart, String lieuArrivee,
                                                 LocalDate dateTrajet, String prenomConducteur, String nomConducteur) {
        return String.format("""
        ✉️ Objet : Confirmez votre trajet EcoRide

        Bonjour %s,

        Votre trajet EcoRide de %s à %s du %s a été marqué comme terminé par le conducteur %s %s.

        Nous vous invitons à vous rendre dans votre espace personnel afin de :

        ✅ Confirmer que tout s’est bien passé
        ⭐ Laisser un avis et une note au conducteur

        Cela nous permet de garantir la qualité de service et d’assurer le bon fonctionnement de la plateforme.
        Si vous avez rencontré un problème, vous pourrez nous le signaler directement dans l’interface.

        👉 Se rendre sur mon espace EcoRide

        Merci pour votre confiance,
        L’équipe EcoRide
        """,
                prenomPassager,
                lieuDepart,
                lieuArrivee,
                dateTrajet.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                prenomConducteur,
                nomConducteur
        );
    }


}
