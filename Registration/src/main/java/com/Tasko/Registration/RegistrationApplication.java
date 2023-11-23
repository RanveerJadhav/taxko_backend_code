package com.Tasko.Registration;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
public class RegistrationApplication 
{

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

}
