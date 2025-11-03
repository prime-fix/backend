package pe.edu.upc.prime.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The main application class for the Prime Fix Platform application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class PrimeFixPlatformApplication {

    /**
     * The main method to run the Spring Boot application.
     *
     * @param args command-line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(PrimeFixPlatformApplication.class, args);
	}

}
