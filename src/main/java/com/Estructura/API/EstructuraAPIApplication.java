package com.Estructura.API;

import static com.Estructura.API.model.Role.ADMIN;
import static com.Estructura.API.model.Role.ARCHITECT;
import static com.Estructura.API.model.Role.CARPENTER;
import static com.Estructura.API.model.Role.CONSTRUCTIONCOMPANY;
import static com.Estructura.API.model.Role.CUSTOMER;
import static com.Estructura.API.model.Role.INTERIORDESIGNER;
import static com.Estructura.API.model.Role.LANDSCAPEARCHITECT;
import static com.Estructura.API.model.Role.MASONWORKER;
import static com.Estructura.API.model.Role.PAINTER;
import static com.Estructura.API.model.Role.RENTER;
import static com.Estructura.API.model.Role.RETAILSTORE;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
				.firstname("Kaveesha")
				.lastname("Samarakoon")
				.email("customer@gmail.com")
				.password("password")
				.contactNo("0321113256")
				.addressLine1("Chilaw Road")
				.addressLine2("Pambala")
				.city("Chilaw")
				.district("Puttalam")
				.role(CUSTOMER).build();

		var admin = RegisterRequest.builder()
				.firstname("Thimira")
				.lastname("Galahitiyawa")
				.email("admin@gmail.com")
				.password("password")
				.role(ADMIN)
				.assignedArea("Super")
				.build();

		var retailStore = RegisterRequest.builder()
				.firstname("Dilmi")
				.lastname("Wijekoon")
				.email("retail@gmail.com")
				.password("password")
				.role(RETAILSTORE)
				.businessName("Damro Showrooms Kottawa")
				.businessContactNo("0342247244")
				.businessCategory("Furniture")
				.registrationNo("FUR01")
				.businessAddressLine1("No:84")
				.businessAddressLine2("Pansala road")
				.businessCity("Kottawa")
				.businessDistrict("Colombo")
				.build();

		var renter = RegisterRequest.builder()
				.firstname("Pubudu")
				.lastname("Anuradha")
				.email("renter@gmail.com")
				.password("password")
				.role(RENTER)
				.businessName("Pubudu Renters")
				.businessContactNo("0112247244")
				.registrationNo("REN01")
				.addressLine1("No:48")
				.addressLine2("Mathugama road")
				.businessCity("Mathugama")
				.businessDistrict("Kaluthara")
				.build();

		var architect = RegisterRequest.builder()
				.firstname("Nimal")
				.lastname("Udawatta")
				.email("architect@gmail.com")
				.password("password")
				.role(ARCHITECT)
				.nic("981022017V")
				.businessContactNo("0342247244")
				.businessName("Udawatte Designs")
				.serviceProviderType("Architect")
				.businessAddressLine1("No:84")
				.businessAddressLine2("Church road")
				.businessCity("Negombo")
				.businessDistrict("Puttalam")
				.sLIARegNumber("SLC22393")
				.website("www.udawattadesigns.com")
				.qualification("BSc in Architecture,University of Moratuwa,Masters in Architecture,University of Moratuwa")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.build();

		var constructionCompany = RegisterRequest.builder()
				.email("construction@gmail.com")
				.password("password")
				.firstname("Pamudi")
				.lastname("Guruge")
				.businessName("Gumudi Construction")
				.businessContactNo("0116547896")
				.businessAddressLine1("No:84")
				.businessAddressLine2("Haldummulla road")
				.businessCity("Ratmalana")
				.businessDistrict("Colombo")
				.nic("9510220170V")
				.website("www.gumudiconstruction.com")
				.registrationNo("CON01")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.specialization("foundations,small homes")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				.role(CONSTRUCTIONCOMPANY)
				.build();

		var interiorDesigner = RegisterRequest.builder()
				.firstname("Harini")
				.lastname("Perera")
				.email("interior@gmail.com")
				.password("password")
				.role(INTERIORDESIGNER)
				.nic("981022015V")
				.businessName("Harini Interior Designs")
				.businessContactNo("0326541236")
				.businessAddressLine1("No:666")
				.businessAddressLine2("Railway road")
				.businessCity("Nugegoda")
				.businessDistrict("Colombo")
				.sLIDRegNumber("SLD22393")
				.website("www.HariniInterior.com")
				.qualification("Bsc in Interior Designing,University of Colombo,Masters in Interior Designing,University of Colombo")
				.serviceAreas(List.of("Colombo", "Kegalle", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.build();

		var landscapeArchitect = RegisterRequest.builder()
				.firstname("Kasun")
				.lastname("Vithanage")
				.email("landscape@gmail.com")
				.password("password")
				.nic("992700666V")
				.businessName("Kasun Landscaping")
				.businessContactNo("0346666666")
				.serviceProviderType("Landscape Architect")
				.businessAddressLine1("No:99")
				.businessAddressLine2("Kandy road")
				.businessCity("Kandy")
				.businessDistrict("Kandy")
				.sLIARegNumber("SLC22394")
				.website("www.KasunScaping.com")
				.qualification("Diploma in Landscape Architect")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.minRate(100000.0)
				.maxRate(1000000.0)
				.role(LANDSCAPEARCHITECT)
				.build();

		var masonWorker = RegisterRequest.builder()
				.email("mason@gmail.com")
				.password("password")
				.firstname("Ravindu")
				.lastname("Gamage")
				.businessName("Ravindu & sons")
				.businessContactNo("0117539515")
				.registrationNo("HOM01")
				.businessAddressLine1("No:16")
				.businessAddressLine2("Ruwanwalisaya road")
				.businessCity("Anuradhapura")
				.businessDistrict("Anuradhapura")
				.nic("9510288170V")
				.website("www.sadanaandsons.com")
				.serviceAreas(List.of("Kandy", "Anuradhapura", "Matale"))
				.specialization("small homes,cottages")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				.role(MASONWORKER)
				.build();

		var painter = RegisterRequest.builder()
				.email("painter@gmail.com")
				.password("password")
				.firstname("Abudu")
				.lastname("Punuradha")
				.businessName("Abudu Painters")
				.businessContactNo("0119999999")
				.registrationNo("ALBL")
				.businessAddressLine1("No:666666")
				.businessAddressLine2("MAthuama road")
				.businessCity("Agalawatta")
				.businessDistrict("Kaluthra")
				.nic("200027000666")
				.website("www.abudupaints.com")
				.registrationNo("ALBL")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.qualification("Item1,Item2")
				.specialization("walls,roof painting")
				.minRate(10000000.0)
				.maxRate(100000000.0)
				.role(PAINTER)
				.build();

		var carpenter = RegisterRequest.builder()
				.email("carpenter@gmail.com")
				.password("password")
				.firstname("Chamara")
				.lastname("Gunasinghe")
				.businessName("Gunasinghe Carpenters")
				.businessContactNo("0326666666")
				.registrationNo("CAR01")
				.businessAddressLine1("No:8")
				.businessAddressLine2("Moonamal road")
				.businessCity("Pannipitiya")
				.businessDistrict("Colombo")
				.nic("200027000999")
				.website("www.GunasingheCarpenter.com")
				.serviceAreas(List.of("Colombo", "Gampaha", "Kaluthra"))
				.qualification("none")
				.specialization("desks,pantry cupboards")
				.minRate(10000000.0)
				.maxRate(100000000.0)
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
