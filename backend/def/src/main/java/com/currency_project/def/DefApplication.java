package com.currency_project.def;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })		// disabling default login screen
public class DefApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefApplication.class, args);
	}

}
