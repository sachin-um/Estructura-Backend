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
@Table(name = "requestTargetProfCategory")
@Entity
public class RequestTargetProfessionalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Role role;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private CustomerRequest customerRequest;
}
