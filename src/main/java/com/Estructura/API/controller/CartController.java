package com.Estructura.API.controller;
import com.Estructura.API.model.OrderRequestBody;
import com.Estructura.API.model.OrderEntity;
import com.Estructura.API.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "*")

public class CartController {

    private final CartService cartService;

    @PostMapping("/single")
    String addToCart(@RequestBody OrderRequestBody orderRequestBody){
        return cartService.addToCart(orderRequestBody);
    }

    @PostMapping("/all")
    String addToCartMultiple(@RequestBody List<OrderRequestBody> orders){
        return cartService.addToCartMultiple(orders);
    }

    @GetMapping()
    List<OrderEntity> findOrderedRetailItems(){
        return cartService.findOrderedRetailItems();
    }

}
