package com.Estructura.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "serviceProvider")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id")
public class ServiceProvider extends User {

    private String nic;
    @Column(nullable = false)
    @NotBlank(message = "Business contact number required")
    private String businessContactNo;
    private ServiceProviderType serviceProviderType;
    @Column()
    private String addressLine1;
    @Column()
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;


}
