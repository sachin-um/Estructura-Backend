package com.Estructura.API.requests.users;

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
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private MultipartFile ProfileImage;
    private String contactNo;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String website;
    private Double minRate; //need to when edit
    private Double maxRate;
    private String businessName;
    private String businessContactNo;
    private String businessCategory;
    private String businessAddressLine1;
    private String businessAddressLine2;
    private String businessCity;
    private String businessDistrict;
    private String specialization;
    private String qualification;
    private List<String> serviceAreas;
}