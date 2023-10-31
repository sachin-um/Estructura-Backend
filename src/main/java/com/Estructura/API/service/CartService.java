package com.Estructura.API.service;

import com.Estructura.API.model.ShoppingCart;
import com.Estructura.API.requests.cart.CartRequest;
import com.Estructura.API.requests.cart.CheckOutRequest;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.cart.CheckOutResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

public interface CartService {
    GenericAddOrUpdateResponse<CartRequest> saveOrUpdateCart(
        @ModelAttribute CartRequest cartRequest) throws IOException;

    GenericAddOrUpdateResponse<CheckOutRequest> checkOut(@ModelAttribute
        CheckOutRequest checkOutRequest);

    ResponseEntity<ShoppingCart> getShoppingCartByCustomer(Integer id);

}
