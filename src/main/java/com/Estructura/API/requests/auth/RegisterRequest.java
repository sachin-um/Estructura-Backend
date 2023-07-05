package com.Estructura.API.requests.auth;

import com.Estructura.API.model.Role;

import jakarta.persistence.Column;
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
    private Role role;
    //Customer
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    //Professional
    private String introduction;
    private Double minRate;
    private Double maxRate;
    //Admin
    private String assignedArea;
    //Architect
    private String sLIARegNumber;
    //RetailStore
    private String businessName;
    private String businessContactNo;
    private String businessCategory;
    private String registrationNo;
    //ServiceProvider

    private String businessAddressLine1;
    private String businessAddressLine2;
    private String businessCity;
    private String businessDistrict;
    private String nic;
    private String serviceProviderType;
}
