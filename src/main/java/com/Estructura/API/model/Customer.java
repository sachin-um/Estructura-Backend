package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customer")
@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {
    @Column()
    private String addressLine1;
    @Column(nullable = false)
    private String addressLine2;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String contactNo;

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<CustomerRequest> customerRequests;
}
