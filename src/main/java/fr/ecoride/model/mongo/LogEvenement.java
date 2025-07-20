package fr.ecoride.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LogEvenement {
    @Id
    private String id;

    private String niveau;
    private String message;
    private String utilisateurId;
    private LocalDateTime date;
}
