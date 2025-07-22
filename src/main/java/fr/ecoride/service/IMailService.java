package fr.ecoride.service;

public interface IMailService {

    void envoyerEmail(String to, String sujet, String contenu);

}
