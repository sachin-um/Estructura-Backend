package com.Estructura.API;

import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.service.AuthenticationService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.Estructura.API.model.Role.ADMIN;
import static com.Estructura.API.model.Role.RETAILOWNER;

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
					.assignedArea("Super")
					.build();
			var retailOwner=RegisterRequest.builder()
					.firstname("Udawatta")
					.lastname("Stores")
					.email("retail@gmail.com")
					.password("password")
					.role(RETAILOWNER)
					.businessName("Udawatta Stores")
					.businessContactNo("0342247244")
					.businessCategory("Hardware")
					.registrationNo("ALAL")
					.businessAddressLine1("No:84")
					.businessAddressLine2("MAthuama road")
					.businessCity("Agalawatta")
					.businessDistrict("Kaluthra")
					.build();
			System.out.println("Admin token :" + service.register(admin).getAccessToken());
			System.out.println("Retail Ower token :" + service.register(retailOwner).getAccessToken());
		};
	}
}
