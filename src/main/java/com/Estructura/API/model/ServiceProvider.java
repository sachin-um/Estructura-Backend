package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="serviceProvider")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="id")
public class ServiceProvider extends User{

    private String nic;
    private Integer contactNumber;
    private String serviceProviderType;
    @Column(nullable = true)
    private String addressLine1;
    @Column(nullable = true)
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;


}
