package com.Estructura.API.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="customer")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Customer extends User{
    @Column(nullable = true)
    private String addressLine1;
    @Column(nullable = false)
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
}
