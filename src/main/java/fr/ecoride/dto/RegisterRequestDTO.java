package fr.ecoride.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDTO implements Serializable {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
    private String dateNaissance;
    private String pseudo;
}
