package com.Estructura.API.requests.auth;

import com.Estructura.API.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
    private MultipartFile profileImage; // Service provider page 7
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
    private String sliaRegNumber;
    // InteriorDesigner
    private String slidRegNumber;
    // RetailStore
    private String businessName;
    private String businessContactNo;
    private String businessCategory;
    private String registrationNo;

    // ServiceProvider
    private String nic;
    private String serviceProviderType;
    private String specialization; // Service provider page 5
    private String qualification;
    private List<String> serviceAreas; // Service provider page 4
}
