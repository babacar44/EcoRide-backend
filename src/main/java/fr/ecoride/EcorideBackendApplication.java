package fr.ecoride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EcorideBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcorideBackendApplication.class, args);
	}

}
