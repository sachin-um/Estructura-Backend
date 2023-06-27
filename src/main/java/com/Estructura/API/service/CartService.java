package com.Estructura.API.service;

import com.Estructura.API.model.OrderEntity;
import com.Estructura.API.model.OrderRequestBody;

import java.util.List;

public interface CartService {

    public String addToCart(OrderRequestBody orderRequestBody);

    public String addToCartMultiple(List<OrderRequestBody> orders);

    public List<OrderEntity> findOrderedRetailItems();

}
