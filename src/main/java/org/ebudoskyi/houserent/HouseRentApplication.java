package org.ebudoskyi.houserent;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HouseRentApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("API_KEY", dotenv.get("API_KEY"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		SpringApplication.run(HouseRentApplication.class, args);
	}

}
