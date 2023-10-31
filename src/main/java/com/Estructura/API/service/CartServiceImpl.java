package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.repository.CartRepository;
import com.Estructura.API.repository.OrderEntityRepository;
import com.Estructura.API.repository.OrderRepository;
import com.Estructura.API.repository.ShoppingCartItemRepository;
import com.Estructura.API.requests.cart.CartRequest;
import com.Estructura.API.requests.cart.CheckOutRequest;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.cart.CheckOutResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final RetailItemService retailItemService;
    private final OrderEntityRepository orderEntityRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;
    private final CustomerService customerService;

    @Override
    public GenericAddOrUpdateResponse<CartRequest> saveOrUpdateCart(
        CartRequest cartRequest) throws IOException {
        GenericAddOrUpdateResponse<CartRequest> response = new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(cartRequest)){
            Optional<Customer> customer=
                customerService.findById(cartRequest.getCustomer_id());

            if (customer.isPresent()){
                cartRepository.deleteAllByCustomer(customer.get());
                ShoppingCart shoppingCart=ShoppingCart.builder()
                    .customer(customer.get())
                    .total(cartRequest.getTotal())
                    .build();
                ShoppingCart savedShoppingCar=cartRepository.save(shoppingCart);
                if (cartRequest.getShoppingCartItems() !=null){
                    cartRequest.getShoppingCartItems().forEach(shoppingCartItem -> {
                        RetailItem retailItem=
                            retailItemService.getItemById(
                                shoppingCartItem.getItemId()).getBody();
                        ShoppingCartItem item=ShoppingCartItem.builder()
                                .quantity(shoppingCartItem.getQuantity())
                                .shoppingCart(savedShoppingCar)
                                .retailItem(retailItem)
                                .build();
                        shoppingCartItemRepository.save(item);
                    });
                }
                response.setSuccess(true);
                response.setId(savedShoppingCar.getCart_id());
            }else {
                response.addError("fatal", "Access denied");
            }
        }else {
            response.addError("fatal", "Bad Request");
        }

        return response;
    }

    @Override
    public GenericAddOrUpdateResponse<CheckOutRequest> checkOut(
        CheckOutRequest checkOutRequest) {
        GenericAddOrUpdateResponse<CheckOutRequest> response=
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(checkOutRequest)){
            Optional<Customer> customer=
                customerService.findById(checkOutRequest.getCustomer_id());
            if (customer.isPresent()){
                Order order=Order.builder()
                    .total(checkOutRequest.getTotalPrice())
                    .createdBy(checkOutRequest.getCustomer_id())
                    .customer(customer.get())
                    .build();
                Order savedOrder=orderRepository.save(order);
                if (checkOutRequest.getShoppingCartItems() != null){
                    checkOutRequest.getShoppingCartItems().forEach(cartItem -> {
                        RetailItem retailItem=
                            retailItemService.getItemById(cartItem.getItemId()).getBody();
                        OrderEntity orderEntity=OrderEntity.builder()
                            .order(order)
                            .quantity(cartItem.getQuantity())
                            .retailItem(retailItem)
                            .build();
                        orderEntityRepository.save(orderEntity);
                    });
                }
                response.setId(savedOrder.getId());
                response.setSuccess(true);
            }else {
                response.addError("fatal", "Access denied");
            }
        }else {
            response.addError("fatal", "Bad Request");
        }
        return response;
    }

    @Override
    public ResponseEntity<ShoppingCart> getShoppingCartByCustomer(Integer id) {
        Optional<Customer> customer=
            customerService.findById(id);
        if (customer.isPresent()){
            ShoppingCart shoppingCart=
                cartRepository.findByCustomer(customer.get());
            if (shoppingCart !=null){
                return ResponseEntity.ok(shoppingCart);
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    ;



}
