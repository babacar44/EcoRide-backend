-- Données initiales pour EcoRide

-- Utilisateurs
INSERT INTO utilisateur (nom, prenom, email, password, telephone, adresse, date_naissance, photo, pseudo, credit, suspendu,role_ecoride)
VALUES
    ('Dupont', 'Jean', 'jean.dupont@example.com',
     '$2a$10$dQtD3BVqu.7vcv.PnPbLG.nO1gKB0nI7JSLBaadn94EJbmb7KVBcG',
     '0601020304', '10 rue de Paris', '1990-05-15', NULL, 'jdupont', 20, false, 'CHAUFFEUR'),

    ('Martin', 'Emma', 'emma.martin@example.com',
     '$2a$10$dQtD3BVqu.7vcv.PnPbLG.nO1gKB0nI7JSLBaadn94EJbmb7KVBcG',
     '0605060708', '22 avenue de Lyon', '1992-08-22', NULL, 'emmartin', 20, false, 'CHAUFFEUR');

-- Rôles
INSERT INTO role (libelle) VALUES ('USER'), ('ADMIN');

-- Liens utilisateur-role
INSERT INTO utilisateur_role (utilisateur_id, role_id) VALUES (1, 1), (2, 1);

-- Marques
INSERT INTO marque (libelle) VALUES ('Renault'), ('Tesla');

-- Voitures
INSERT INTO voiture (modele, immatriculation, energie, couleur, date_premiere_immatriculation,nb_places, marque_id,utilisateur_id)
VALUES
    ('Zoé', 'AB-123-CD', 'ELECTRIQUE', 'Bleu', '2022-03-01', 4,2,1),
    ('Model 3', 'CD-456-EF', 'ELECTRIQUE', 'Blanc', '2023-01-15', 5,1,2);

-- Covoiturages
INSERT INTO covoiturage (
    date_depart, heure_depart, lieu_depart,
    date_arrivee, heure_arrivee, lieu_arrivee,
    statut, nb_place, prix_personne, utilisateur_id, voiture_id
) VALUES
      ('2025-07-17', '15:29:00', 'Lyon', '2025-07-17', '17:29:00', 'Grenoble', 'FERME', 1, 36.32, 2, 2),
      ('2025-07-18', '09:31:00', 'Lyon', '2025-07-18', '12:31:00', 'Grenoble', 'FERME', 1, 28.96, 1, 1),
      ('2025-07-19', '16:28:00', 'Lyon', '2025-07-19', '18:28:00', 'Grenoble', 'OUVERT', 3, 12.49, 2, 2),
      ('2025-07-20', '11:42:00', 'Toulouse', '2025-07-20', '14:42:00', 'Bordeaux', 'OUVERT', 3, 23.46, 1, 1),
      ('2025-07-21', '07:50:00', 'Toulouse', '2025-07-21', '09:50:00', 'Bordeaux', 'FERME', 2, 34.2, 1, 1),
      ('2025-07-22', '12:40:00', 'Toulouse', '2025-07-22', '17:40:00', 'Bordeaux', 'OUVERT', 2, 11.62, 2, 2),
      ('2025-07-23', '12:04:00', 'Lyon', '2025-07-23', '16:04:00', 'Paris', 'OUVERT', 3, 34.51, 1, 1),
      ('2025-07-24', '16:38:00', 'Lyon', '2025-07-24', '21:38:00', 'Paris', 'FERME', 1, 27.66, 2, 2),
      ('2025-07-25', '06:14:00', 'Marseille', '2025-07-25', '08:14:00', 'Nice', 'FERME', 1, 14.73, 2, 2),
      ('2025-07-26', '10:52:00', 'Lyon', '2025-07-26', '13:52:00', 'Grenoble', 'OUVERT', 3, 35.89, 2, 2),
      ('2025-07-27', '15:36:00', 'Lyon', '2025-07-27', '19:36:00', 'Paris', 'FERME', 2, 22.33, 2, 2),
      ('2025-07-28', '16:52:00', 'Marseille', '2025-07-28', '20:52:00', 'Nice', 'FERME', 1, 27.77, 2, 2),
      ('2025-07-29', '06:52:00', 'Toulouse', '2025-07-29', '11:52:00', 'Bordeaux', 'OUVERT', 2, 36.55, 2, 2),
      ('2025-07-30', '13:49:00', 'Lyon', '2025-07-30', '15:49:00', 'Paris', 'OUVERT', 2, 14.73, 2, 2),
      ('2025-07-31', '17:35:00', 'Lyon', '2025-07-31', '21:35:00', 'Grenoble', 'FERME', 2, 11.84, 2, 2),
      ('2025-08-01', '12:27:00', 'Lyon', '2025-08-01', '15:27:00', 'Paris', 'FERME', 2, 21.91, 1, 1),
      ('2025-08-02', '11:03:00', 'Lyon', '2025-08-02', '15:03:00', 'Grenoble', 'OUVERT', 4, 39.49, 2, 2),
      ('2025-08-03', '12:53:00', 'Lyon', '2025-08-03', '16:53:00', 'Grenoble', 'FERME', 2, 31.77, 1, 1),
      ('2025-08-04', '11:07:00', 'Lyon', '2025-08-04', '13:07:00', 'Paris', 'OUVERT', 4, 16.19, 1, 1),
      ('2025-08-05', '06:39:00', 'Marseille', '2025-08-05', '10:39:00', 'Nice', 'OUVERT', 4, 16.24, 1, 1),
      ('2025-08-06', '15:28:00', 'Lyon', '2025-08-06', '17:28:00', 'Paris', 'OUVERT', 4, 12.62, 1, 1),
      ('2025-08-07', '11:53:00', 'Lyon', '2025-08-07', '13:53:00', 'Paris', 'OUVERT', 4, 37.07, 2, 2),
      ('2025-08-08', '09:01:00', 'Lyon', '2025-08-08', '11:01:00', 'Paris', 'FERME', 1, 18.7, 2, 2),
      ('2025-08-09', '08:25:00', 'Lyon', '2025-08-09', '10:25:00', 'Paris', 'OUVERT', 1, 19.37, 1, 1),
      ('2025-08-10', '10:34:00', 'Toulouse', '2025-08-10', '13:34:00', 'Bordeaux', 'OUVERT', 1, 12.5, 2, 2),
      ('2025-08-11', '17:16:00', 'Lyon', '2025-08-11', '21:16:00', 'Paris', 'FERME', 1, 27.3, 1, 1),
      ('2025-08-12', '17:27:00', 'Lyon', '2025-08-12', '19:27:00', 'Grenoble', 'FERME', 1, 34.46, 1, 1),
      ('2025-08-13', '10:40:00', 'Lyon', '2025-08-13', '13:40:00', 'Paris', 'OUVERT', 1, 20.69, 1, 1),
      ('2025-08-14', '15:03:00', 'Toulouse', '2025-08-14', '17:03:00', 'Bordeaux', 'OUVERT', 1, 14.89, 2, 2),
      ('2025-08-15', '17:50:00', 'Lyon', '2025-08-15', '20:50:00', 'Paris', 'FERME', 1, 21.93, 1, 1),
      ('2025-08-16', '09:17:00', 'Lyon', '2025-08-16', '13:17:00', 'Paris', 'OUVERT', 1, 11.6, 1, 1),
      ('2025-08-17', '15:17:00', 'Lyon', '2025-08-17', '18:17:00', 'Paris', 'OUVERT', 1, 25.91, 2, 2);
-- Avis
INSERT INTO avis (commentaire, note, statut, utilisateur_id)
VALUES
    ('Très bon conducteur', '5', 'VALIDE', 2),
    ('Voiture propre et trajet agréable', '4', 'VALIDE', 1);

-- Paramètres
INSERT INTO parametre (propriete, valeur)
VALUES
    ('nb_max_covoiturage_jour', '3'),
    ('plafond_credit', '50');

-- Préférence
INSERT INTO preference(label, utilisateur_id)
VALUES
    ('Fumeur', '1'),
    ('Animaux', '1'),
    ('Animaux', '2');

-- Configuration
INSERT INTO configuration DEFAULT VALUES;