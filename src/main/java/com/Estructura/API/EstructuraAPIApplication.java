package com.Estructura.API;

import com.Estructura.API.service.AuthenticationService;
import com.Estructura.API.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.Estructura.API.model.Role.ADMIN;

@SpringBootApplication
public class EstructuraAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstructuraAPIApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	){
		return args -> {
			var admin=RegisterRequest.builder()
					.firstname("Admin")
					.lastname("ADmin")
					.email("admin@gmail.com")
					.password("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token :" + service.register(admin).getAccessToken());
		};
	}
}
