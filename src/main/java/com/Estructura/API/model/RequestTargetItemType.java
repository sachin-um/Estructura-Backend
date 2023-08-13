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
@Table(name="requestTargetItemType")
@Entity
public class RequestTargetItemType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RetailItemType retailItemType;

    @ManyToOne
    @JoinColumn(name="request_id")
    private CustomerRequest customerRequest;
}
