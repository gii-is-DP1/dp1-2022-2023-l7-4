package org.springframework.samples.tyrantsOfTheUnderdark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication()
public class TyrantsOfTheUnderdarkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TyrantsOfTheUnderdarkApplication.class, args);
	}

}
