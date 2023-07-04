package com.Estructura.API.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="retailstore")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class RetailStore extends User{
    @Column(nullable = false)
    @NotBlank(message = "Business name required")
    private String businessName;
    @Column(nullable = false)
    @NotBlank(message = "Business contact number required")
    private String businessContactNo;
    @Column(nullable = false)
    @NotBlank(message = "Business Category Required")
    private String businessCategory;
    @Column(nullable = false)
    @NotBlank(message = "Business Registration Number Required")
    private String registrationNo;
    @Column(nullable = true)
    private String addressLine1;
    @Column(nullable = false)
    private String addressLine2;
    @Column(nullable = false)
    @NotBlank(message = "City is required")
    private String city;
    @Column(nullable = false)
    private String district;

}
