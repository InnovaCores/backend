package pe.edu.upc.managewise.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Clase principal de Spring Boot: arranca la aplicación y activa el auditoría JPA para registro de cambios
@SpringBootApplication
@EnableJpaAuditing
public class ManagewiseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagewiseBackendApplication.class, args);
	}

}
