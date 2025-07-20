package fr.ecoride.service.impl;

import fr.ecoride.model.mongo.LogEvenement;
import fr.ecoride.repository.MongoLogRepository;
import fr.ecoride.service.ILogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements ILogService {

    private final MongoLogRepository logRepository;

    @Override
    public void enregistrer(String niveau, String message, String utilisateurId) {
        LogEvenement log = LogEvenement.builder()
                .niveau(niveau)
                .message(message)
                .utilisateurId(utilisateurId)
                .date(LocalDateTime.now())
                .build();

        logRepository.save(log);
    }
}
