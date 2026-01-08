package com.UMC_9th_Hackathon.UMC_9th_Hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Umc9thHackathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(Umc9thHackathonApplication.class, args);
	}

}
