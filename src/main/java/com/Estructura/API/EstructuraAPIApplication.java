package com.Estructura.API;

import static com.Estructura.API.model.Role.*;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.Estructura.API.model.Renter;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.service.AuthenticationService;

@SpringBootApplication
public class EstructuraAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstructuraAPIApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service) {
		return args -> {
			RegisterDummies(service);
		};
	}

	private void RegisterDummies(
			AuthenticationService service) {
		var customer = RegisterRequest.builder()
				.firstname("Burampi")
				.lastname("Appu")
				.email("customer@gmail.com")
				.password("password")
				.contactNo("0321113256")
				.addressLine1("Chilaw Road")
				.addressLine2("Pambala")
				.city("Chilaw")
				.district("Puttalam")
				.role(CUSTOMER).build();

		var admin = RegisterRequest.builder()
				.firstname("Admin")
				.lastname("ADmin")
				.email("admin@gmail.com")
				.password("password")
				.role(ADMIN)
				.assignedArea("Super")
				.build();

		var retailStore = RegisterRequest.builder()
				.firstname("Udawatta")
				.lastname("Stores")
				.email("retail@gmail.com")
				.password("password")
				.role(RETAILSTORE)
				.businessName("Udawatta Stores")
				.businessContactNo("0342247244")
				.businessCategory("Hardware")
				.registrationNo("ALAL")
				.businessAddressLine1("No:84")
				.businessAddressLine2("Mathugama road")
				.businessCity("Mathugama")
				.businessDistrict("Kaluthara")
				.build();

		var renter = RegisterRequest.builder()
				.firstname("Aneru")
				.lastname("Sakarawita")
				.email("renter@gmail.com")
				.password("password")
				.role(RENTER)
				.businessName("Sakarawita Machines")
				.businessContactNo("0112247244")
				.registrationNo("ALBL")
				.addressLine1("No:48")
				.addressLine2("Mathugama road")
				.businessCity("Mathugama")
				.businessDistrict("Kaluthara")
				.build();

		var architect = RegisterRequest.builder()
				.firstname("Udawatta")
				.lastname("Designs")
				.email("architect@gmail.com")
				.password("password")
				.role(ARCHITECT)
				.nic("981022017V")
				.businessContactNo("0342247244")
				.businessName("Udawatte Designs")
				.serviceProviderType("Architect")
				.businessAddressLine1("No:84")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				.sLIARegNumber("SLC22393")
				.website("www.udawattadesigns.com")
				.qualification("Item1,Item2")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.build();

		var constructionCompany = RegisterRequest.builder()
				// Page 1
				.email("construction@gmail.com")
				.password("password")
				// Page 2
				.firstname("Gumudi")
				.lastname("Paruge")
				.businessName("Gumudi Construction")
				.businessContactNo("0116547896")
				// Address Line should be converted from request??
				.businessAddressLine1("No:84")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				.teamSize(50)
				// Page 3
				.nic("9510220170V")
				.website("www.gumudiconstruction.com")
				.registrationNo("ALBL") // Certificate not here
				// Page 4
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.specialization("foundations,small homes")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				// Photo?
				.role(CONSTRUCTIONCOMPANY)
				.build();

		var interiorDesigner = RegisterRequest.builder()
				.firstname("Uchin")
				.lastname("Samayangana")
				.email("interior@gmail.com")
				.password("password")
				.role(INTERIORDESIGNER)
				.nic("981022015V")
				.businessName("Uchi Interior Designs")
				.businessContactNo("0326541236")
				.businessAddressLine1("No:666")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				.sLIDRegNumber("SLD22393")
				.website("www.uchinsamayangana.com")
				.qualification("Item1,Item2")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.build();

		var landscapeArchitect = RegisterRequest.builder()
				.firstname("Udana")
				.lastname("Saranehru")
				.email("landscape@gmail.com")
				.password("password")
				.nic("992700666V")
				.businessName("Saranehru Landscaping")
				.businessContactNo("0346666666")
				.serviceProviderType("Landscape Architect")
				.businessAddressLine1("No:99")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				.sLIARegNumber("SLC22394")
				.website("www.saranerudana.com")
				.qualification("Item1,Item2")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.role(LANDSCAPEARCHITECT)
				.build();

		var masonWorker = RegisterRequest.builder()
				// Page 1
				.email("mason@gmail.com")
				.password("password")
				// Page 2
				.firstname("Unuru")
				.lastname("Sadana")
				.businessName("Sadana & sons")
				.businessContactNo("0117539515")
				.registrationNo("ALBL")
				// Address Line should be converted from request??
				.businessAddressLine1("No:94")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				// Page 3
				.nic("9510288170V")
				.website("www.sadanaandsons.com")
				.registrationNo("ALBL") // Certificate not here
				// Page 4
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.specialization("small homes,cottages")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				// Photo?
				.role(MASONWORKER)
				.build();

		var painter = RegisterRequest.builder()
				// Page 1
				.email("painter@gmail.com")
				.password("password")
				// Page 2
				.firstname("Abudu")
				.lastname("Punuradha")
				.businessName("Abudu Painters")
				.businessContactNo("0119999999")
				.registrationNo("ALBL")
				// Address Line should be converted from request??
				.businessAddressLine1("No:666666")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				// Page 3
				.nic("200027000666")
				.website("www.abudupaints.com")
				.registrationNo("ALBL") // Certificate not here
				// Page 4
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.qualification("Item1,Item2")
				.specialization("walls,roof painting")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				// Photo?
				.role(PAINTER)
				.build();

		var carpenter = RegisterRequest.builder()
				// Page 1
				.email("carpenter@gmail.com")
				.password("password")
				// Page 2
				.firstname("Sabudu")
				.lastname("Puthurusinghe")
				.businessName("Sabudu Carpenters")
				.businessContactNo("0326666666")
				.registrationNo("ALBL")
				// Address Line should be converted from request??
				.businessAddressLine1("No:999999")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				// Page 3
				.nic("200027000999")
				.website("www.sabuduwood.com")
				.registrationNo("ALBL") // Certificate not here
				// Page 4
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.qualification("Item1,Item2")
				.specialization("desks,pantry cupboards")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				// Photo?
				.role(CARPENTER)
				.build();

		System.out.println("Architect:" + service.register(architect, true).getAccessToken()); // ✅
		System.out.println("Admin token :" + service.register(admin, true).getAccessToken()); // ✅
		System.out.println("Retail Owner token :" + service.register(retailStore, true).getAccessToken()); // ✅
		System.out.println("Customer token :" + service.register(customer, true).getAccessToken()); // ✅ 
		System.out.println("Renter token :" + service.register(renter, true).getAccessToken()); // ✅
		System.out.println("Con Company token :" + service.register(constructionCompany, true).getAccessToken()); // ✅
		System.out.println("Interior token :" + service.register(interiorDesigner, true).getAccessToken()); // ✅
		System.out.println("Mason token :" + service.register(masonWorker, true).getAccessToken()); // ✅
		System.out.println("Landscape token :" + service.register(landscapeArchitect, true).getAccessToken()); // ✅
		System.out.println("Painter token :" + service.register(painter, true).getAccessToken()); // ✅
		System.out.println("carpenter token :" + service.register(carpenter, true).getAccessToken()); // ✅
	}
}
