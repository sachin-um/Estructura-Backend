package com.Estructura.API.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "retail_Item")
public class RetailItem {

    @Id
    @SequenceGenerator(
            name = "retail_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "retail_item_sequence"
    )
    private long id;

    @Enumerated(EnumType.STRING)
    private RetailItemType retailItemType;

    //@Column(name = "item_Name",length = 255,nullable = false)
    private String name;

    //@Column(name="price", columnDefinition = "numeric(10,2)")
    private Double price;

    private String image;

    private String description;

    private Integer quantity;// different quantity types

    public RetailItem(RetailItemType retailItemType, String name, Double price, String image, String description, Integer quantity){
        this.retailItemType = retailItemType;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
    }


}
