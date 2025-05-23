package com.currency.bb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })		// disabling default login screen
public class BbApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbApplication.class, args);
	}

}
