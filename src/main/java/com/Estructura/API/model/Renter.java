package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="renter")
@Entity
@PrimaryKeyJoinColumn(name="id")
public class Renter extends ServiceProvider{
    @Column(nullable = false)
    @NotBlank(message = "Business name required")
    private String businessName;
    @Column(nullable = false)
    @NotBlank(message = "Business Registration Number Required")
    private String registrationNo;
    @JsonIgnore
    @OneToMany(mappedBy = "renter", cascade = CascadeType.ALL)
    private List<RentingItem> rentingItems;
}
