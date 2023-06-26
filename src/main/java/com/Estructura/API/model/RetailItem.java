package com.Estructura.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "retail_Item")
public class RetailItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "item_Name",length = 255,nullable = false)
    private String name;

    @Column(precision = 8, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    private Double price;

    private String description;

    private String category;

    private Integer quantity;// different quantity types




}
