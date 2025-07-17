package org.ebudoskyi.houserent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HouseRentApplication {

	public static void main(String[] args) {
		SpringApplication.run(HouseRentApplication.class, args);
	}

}
