package com.Estructura.API.controller;

import com.Estructura.API.requests.cart.CartRequest;
import com.Estructura.API.requests.cart.CheckOutRequest;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.service.CartService;
import com.Estructura.API.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cart")


public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/create")
    public GenericAddOrUpdateResponse<CartRequest> createCart(
        @ModelAttribute CartRequest cartRequest) throws IOException {
        return cartService.saveOrUpdateCart(cartRequest);
    }

    @PostMapping("/checkout")
    public GenericAddOrUpdateResponse<CheckOutRequest> checkout(@ModelAttribute CheckOutRequest checkOutRequest){
        return cartService.checkOut(checkOutRequest);
    }

}
