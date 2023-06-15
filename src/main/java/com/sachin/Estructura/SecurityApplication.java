package com.sachin.Estructura;

import com.sachin.Estructura.auth.AuthenticationService;
import com.sachin.Estructura.auth.RegisterRequest;
import com.sachin.Estructura.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.sachin.Estructura.model.Role.ADMIN;

@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
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
