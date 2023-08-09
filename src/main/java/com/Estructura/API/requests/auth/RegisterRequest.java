package com.Estructura.API.requests.auth;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.Estructura.API.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "First name is required")
    private String firstname;
    @NotBlank(message = "Last name is required")
    private String lastname;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private String confirmPassword;
    private Role role;
    private MultipartFile ProfileImage; // Service provider page 7
    // Customer
    private String contactNo;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    // Professional
    private String website; // Construction Company
    private Integer teamSize;
    private MultipartFile registrationCertificate; // Construction Company
    private String introduction;
    private Double minRate; // Service provider page 6
    private Double maxRate; // Service provider page 6
    // Admin
    private String assignedArea;
    // Architect
    private String sLIARegNumber;
    // InteriorDesigner
    private String sLIDRegNumber;
    // RetailStore
    private String businessName;
    private String businessContactNo;
    private String businessCategory;
    private String registrationNo;

    // ServiceProvider

    private String businessAddressLine1;
    private String businessAddressLine2;
    private String businessCity;
    private String businessDistrict;
    private String nic;
    private String serviceProviderType;
    private String specialization; // Service provider page 5
    private String qualification;
    private List<String> serviceAreas; // Service provider page 4
}
