package fr.ecoride.service;

public interface ILogService {

    void enregistrer(String niveau, String message, String utilisateurId);
}
