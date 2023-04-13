package com.api.hotelavailability;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HotelAvailabilityApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelAvailabilityApplication.class, args);
	}

}
