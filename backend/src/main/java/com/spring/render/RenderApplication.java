package com.spring.render;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RenderApplication {

	public static void main(String[] args) {
		// Banc d'essai SmartDeploy : configuration obligatoire absente au démarrage
		if (System.getenv("PANNE_CONFIGURATION_OBLIGATOIRE") == null) {
			throw new IllegalStateException("Panne simulée : variable PANNE_CONFIGURATION_OBLIGATOIRE absente, démarrage impossible");
		}
		SpringApplication.run(RenderApplication.class, args);
	}

}
