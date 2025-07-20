DROP TABLE IF EXISTS utilisateur_role, avis,participation, covoiturage, voiture, marque, parametre, configuration, role, utilisateur CASCADE;

-- UTILISATEUR
CREATE TABLE utilisateur (
     utilisateur_id SERIAL PRIMARY KEY,
     nom VARCHAR(100) NOT NULL,
     prenom VARCHAR(100) NOT NULL,
     email VARCHAR(150) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     telephone VARCHAR(20),
     adresse VARCHAR(255),
     date_naissance DATE,
     photo VARCHAR(255),
     pseudo VARCHAR(100) NOT NULL,
     credit INT,
     suspendu BOOLEAN DEFAULT FALSE,
     role_ecoride VARCHAR(255)
);

-- ROLE
CREATE TABLE role (
    role_id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL
);

-- UTILISATEUR_ROLE (relation many-to-many)
CREATE TABLE utilisateur_role (
    utilisateur_id INT REFERENCES utilisateur(utilisateur_id) ON DELETE CASCADE,
    role_id INT REFERENCES role(role_id) ON DELETE CASCADE,
    PRIMARY KEY (utilisateur_id, role_id)
);

-- MARQUE
CREATE TABLE marque (
    marque_id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL
);

-- VOITURE
CREATE TABLE voiture (
    voiture_id SERIAL PRIMARY KEY,
    modele VARCHAR(100) NOT NULL,
    immatriculation VARCHAR(50) NOT NULL UNIQUE,
    energie VARCHAR(50) NOT NULL,
    couleur VARCHAR(50),
    date_premiere_immatriculation DATE NOT NULL,
    nb_places INT,
    marque_id INT REFERENCES marque(marque_id),
    utilisateur_id INT REFERENCES utilisateur(utilisateur_id)

);

-- COVOITURAGE
CREATE TABLE covoiturage (
    covoiturage_id SERIAL PRIMARY KEY,
    date_depart DATE NOT NULL,
    heure_depart TIME NOT NULL,
    lieu_depart VARCHAR(150) NOT NULL,
    date_arrivee DATE NOT NULL,
    heure_arrivee TIME NOT NULL,
    lieu_arrivee VARCHAR(150) NOT NULL,
    statut VARCHAR(50),
    nb_place INT,
    prix_personne FLOAT,
    utilisateur_id INT REFERENCES utilisateur(utilisateur_id),
    voiture_id INT REFERENCES voiture(voiture_id)
);

-- PARTICIPATION
CREATE TABLE participation (
      id SERIAL PRIMARY KEY,
      confirme BOOLEAN DEFAULT FALSE,
      valide BOOLEAN DEFAULT FALSE,
      avis_statut BOOLEAN DEFAULT FALSE,
      avis_commentaire TEXT,
      avis_note INT,
      avis_valide BOOLEAN DEFAULT FALSE,
      avis_date DATE,
      publier_avis VARCHAR(10),
      passager_id INT REFERENCES utilisateur(utilisateur_id),
      covoiturage_id INT REFERENCES covoiturage(covoiturage_id)
);

-- AVIS
CREATE TABLE avis (
    avis_id SERIAL PRIMARY KEY,
    commentaire TEXT,
    note INT,
    statut VARCHAR(20),
    auteur_id INT REFERENCES utilisateur(utilisateur_id),
    destinataire_id INT REFERENCES utilisateur(utilisateur_id)
);

-- PARAMETRE
CREATE TABLE parametre (
    parametre_id SERIAL PRIMARY KEY,
    propriete VARCHAR(100) NOT NULL,
    valeur VARCHAR(255) NOT NULL
);


-- CONFIGURATION
CREATE TABLE configuration (
    id_configuration SERIAL PRIMARY KEY
);
