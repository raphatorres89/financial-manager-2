package com.raphaowl.financialowl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class FinancialOwlApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialOwlApplication.class, args);
	}

}
