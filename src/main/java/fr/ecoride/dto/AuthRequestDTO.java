package fr.ecoride.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequestDTO implements Serializable {
    private String email;
    private String password;
}
