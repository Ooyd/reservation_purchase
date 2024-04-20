package com.example.ReservationPurchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReservationPurchaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationPurchaseApplication.class, args);
	}

}
