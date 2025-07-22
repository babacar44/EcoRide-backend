package fr.ecoride.service.impl;

import fr.ecoride.dto.UserSpaceForm;
import fr.ecoride.dto.UserSpaceResponseDTO;
import fr.ecoride.dto.VoitureDTO;
import fr.ecoride.exception.NotFoundException;
import fr.ecoride.model.Marque;
import fr.ecoride.model.Preference;
import fr.ecoride.model.Utilisateur;
import fr.ecoride.model.Voiture;
import fr.ecoride.repository.MarqueRepository;
import fr.ecoride.repository.PreferenceRepository;
import fr.ecoride.repository.UtilisateurRepository;
import fr.ecoride.repository.VoitureRepository;
import fr.ecoride.service.IEspaceUtilisateurService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EspaceUtilisateurServiceImpl implements IEspaceUtilisateurService {
    Logger logger = LoggerFactory.getLogger(EspaceUtilisateurServiceImpl.class);
    private final UtilisateurRepository utilisateurRepository;
    private final MarqueRepository marqueRepository;
    private final VoitureRepository voitureRepository;
    private final PreferenceRepository preferenceRepository;


    @Override
    @Transactional
    public void enregistrer(UserSpaceForm form, String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        if (form.getRoleEcoride().equalsIgnoreCase("CHAUFFEUR") || form.getRoleEcoride().equalsIgnoreCase("BOTH") && !form.getVoitures().isEmpty()) {
            for (UserSpaceForm.VoitureForm voitureForm : form.getVoitures()){
                // Créer ou mettre à jour la voiture
                Marque marque = marqueRepository.findByLibelleIgnoreCase(voitureForm.getMarque())
                        .orElseGet(() -> marqueRepository.save(new Marque(null, voitureForm.getMarque())));

                var voiture = voitureRepository.findVoitureByImmatriculationIgnoreCase(voitureForm.getImmatriculation());
                Voiture voitureNew;
                if (voiture.isEmpty()){
                    voitureNew = new Voiture();
                    voitureNew.setImmatriculation(voitureForm.getImmatriculation());
                    voitureNew.setDatePremiereImmatriculation(LocalDate.parse(voitureForm.getDatePremiereImmatriculation()));
                    voitureNew.setModele(voitureForm.getModele());
                    voitureNew.setCouleur(voitureForm.getCouleur());
                    voitureNew.setEnergie(voitureForm.getEnergie());
                    voitureNew.setNbPlaces(voitureForm.getNbPlaces());
                    voitureNew.setUtilisateur(utilisateur);
                    voitureNew.setMarque(marque);
                    voitureRepository.save(voitureNew);
                }else {
                    voiture.get().setImmatriculation(voitureForm.getImmatriculation());
                    voiture.get().setDatePremiereImmatriculation(LocalDate.parse(voitureForm.getDatePremiereImmatriculation()));
                    voiture.get().setModele(voitureForm.getModele());
                    voiture.get().setCouleur(voitureForm.getCouleur());
                    voiture.get().setEnergie(voitureForm.getEnergie());
                    voiture.get().setNbPlaces(voitureForm.getNbPlaces());
                    voiture.get().setUtilisateur(utilisateur);
                    voiture.get().setMarque(marque);
                    voitureRepository.save(voiture.get());
                }
            }
            // Enregistrer les préférences
            preferenceRepository.deleteByUtilisateur(utilisateur);
            if (form.getPreferences() != null) {
                if (!form.getPreferences().getAutres().isBlank()) {
                    preferenceRepository.save(new Preference(null, form.getPreferences().getAutres(), utilisateur));
                }
                if (form.getPreferences().isFumeur()) {
                    preferenceRepository.save(new Preference(null, "Fumeur", utilisateur));
                }
                if (form.getPreferences().isAnimaux()) {
                    preferenceRepository.save(new Preference(null, "Animaux", utilisateur));
                }
            }
        }
        // Mettre à jour le rôle si besoin
        if (form.getRoleEcoride().equalsIgnoreCase("PASSAGER")) {
            utilisateur.setRoleEcoride("PASSAGER");
        } else if (form.getRoleEcoride().equalsIgnoreCase("CHAUFFEUR")) {
            utilisateur.setRoleEcoride("CHAUFFEUR");
        } else if (form.getRoleEcoride().equalsIgnoreCase("BOTH")) {
            utilisateur.setRoleEcoride("BOTH");
        }
        utilisateurRepository.save(utilisateur);
    }

    @Override
    @Transactional
    public UserSpaceResponseDTO getInfos(Utilisateur utilisateur) {
        Utilisateur utilisateur1 = utilisateurRepository.findById(utilisateur.getUtilisateurId())
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable avec id " + utilisateur.getUtilisateurId()));
        // Rôle
        String roleEcoride = utilisateur1.getRoleEcoride();

        // Voiture
        var voitures = voitureRepository.findAllByUtilisateur(utilisateur1);
        UserSpaceResponseDTO userSpaceResponseDTO = UserSpaceResponseDTO.builder().build();
        List<VoitureDTO> voitureDTOS = new ArrayList<>();
        if (!voitures.isEmpty()) {
            for (Voiture voiture : voitures) {
                var voitureDTO = VoitureDTO.builder()
                        .immatriculation(voiture.getImmatriculation())
                        .datePremiereImmatriculation(voiture.getDatePremiereImmatriculation().toString())
                        .marque(voiture.getMarque().getLibelle())
                        .modele(voiture.getModele())
                        .couleur(voiture.getCouleur())
                        .energie(voiture    .getEnergie())
                        .nbPlaces(voiture.getNbPlaces())
                        .build();
                voitureDTOS.add(voitureDTO);
            }
        }

        // Préférences
        List<Preference> prefs = preferenceRepository.findByUtilisateur(utilisateur);
        boolean fumeur = prefs.stream().anyMatch(p -> p.getLabel().equalsIgnoreCase("Fumeur"));
        boolean animaux = prefs.stream().anyMatch(p -> p.getLabel().equalsIgnoreCase("Animaux"));
        String autres = prefs.stream()
                .filter(p -> !p.getLabel().equalsIgnoreCase("Fumeur") && !p.getLabel().equalsIgnoreCase("Animaux"))
                .map(Preference::getLabel)
                .collect(Collectors.joining(", "));

        return UserSpaceResponseDTO.builder()
                .roleEcoride(roleEcoride)
                .voitures(voitureDTOS)
                .preferences(UserSpaceResponseDTO.PreferenceDTO.builder()
                        .fumeur(fumeur)
                        .animaux(animaux)
                        .autres(autres)
                        .build())
                .build();
    }

    @Override
    @Transactional
    public Boolean supprimerVoiture(VoitureDTO voitureDTO, Utilisateur utilisateur) {
        try {
            voitureRepository.deleteVoitureByImmatriculationIgnoreCaseAndUtilisateur_UtilisateurId(voitureDTO.getImmatriculation(), utilisateur.getUtilisateurId());
            return true;
        }catch (Exception exception){
            logger.error(exception.getMessage(), exception);
            return false;
        }
    }

}
