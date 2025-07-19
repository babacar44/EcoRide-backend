package fr.ecoride.service.impl;

import fr.ecoride.service.IMailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImp implements IMailService {

    private final JavaMailSender mailSender;

    public MailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void envoyerEmail(String to, String sujet, String contenu) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(sujet);
        message.setText(contenu);
        mailSender.send(message);
    }
}
