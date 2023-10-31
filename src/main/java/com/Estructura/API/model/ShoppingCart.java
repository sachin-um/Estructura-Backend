package com.Estructura.API.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "shopping_cart")

public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_id;


    @JoinColumn(name = "customer_id")
    @OneToOne
    @JsonIgnore
    private Customer customer;

    private Double total;


    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItem> shoppingCartItems;


}
