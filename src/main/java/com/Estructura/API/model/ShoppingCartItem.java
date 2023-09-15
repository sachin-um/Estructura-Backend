package com.Estructura.API.model;

import jakarta.persistence.*;

@Entity
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_item_id;

    @JoinColumn(name = "cart_id")
    @ManyToOne
    private ShoppingCart shoppingCart;

    @JoinColumn(name = "item_id")
    @ManyToOne
    private RetailItem retailItem;

    private Integer quantity;

}
