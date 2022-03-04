package com.poly.SOF3021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AssigmentSof3021Application {

	public static void main(String[] args) {
		SpringApplication.run(AssigmentSof3021Application.class, args);
	}

}
