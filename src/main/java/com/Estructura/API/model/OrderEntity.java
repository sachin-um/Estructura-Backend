package com.Estructura.API.model;

import com.Estructura.API.model.RetailItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class OrderEntity {
    @Id
    @SequenceGenerator(
            name = "order_entity_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_entity_sequence"
    )

    private Long id;
    private Integer quantity;

    @OneToOne
    private RetailItem retailItem;

    public OrderEntity(Integer quantity, RetailItem retailItem){
        this.quantity = quantity;
        this.retailItem = retailItem;
    }

}
