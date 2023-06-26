package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="serviceProvider")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id")
public class ServiceProvider extends User{

//    private String firstName;
//    private String lastName;
    private String nic;
    private Integer contactNumber;
    private String serviceProviderType;
    private String introduction;
    @Column(columnDefinition = "numeric(10,2)")
    private Double minRate;
    @Column(columnDefinition = "numeric(10,2)")
    private Double maxRate;
    @Column(nullable = true)
    private String addressLine1;
    @Column(nullable = true)
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
}
