package com.Estructura.API.requests.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CheckOutRequest{
    private Double totalPrice;
    private Integer customer_id;

    private List<CartItem> shoppingCartItems;
}
