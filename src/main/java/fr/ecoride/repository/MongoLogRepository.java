package fr.ecoride.repository;

import fr.ecoride.model.mongo.LogEvenement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLogRepository  extends MongoRepository<LogEvenement, String> {

}
