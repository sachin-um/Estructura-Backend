package com.Estructura.API.service;

import com.Estructura.API.model.OrderEntity;
import com.Estructura.API.model.OrderRequestBody;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.model.ShoppingCart;
import com.Estructura.API.repository.CartRepository;
import com.Estructura.API.repository.OrderEntityRepository;
import com.Estructura.API.userinfo.UserInfo;
import lombok.AllArgsConstructor;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService{

    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final RetailItemService retailItemService;
    @Autowired
    private final OrderEntityRepository orderEntityRepository;

    @Override
    @Transactional
    public String addToCart(OrderRequestBody orderRequestBody){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) auth.getPrincipal();
        Optional<ShoppingCart> shoppingCartCheck = cartRepository.findByUserInfo(userInfo);

        ShoppingCart shoppingCart;
        if(!shoppingCartCheck.isPresent()){
            shoppingCart = new ShoppingCart(userInfo);
            cartRepository.save(shoppingCart);
        }else{
            shoppingCart = shoppingCartCheck.get();
        }

        Optional<RetailItem> retailItem = retailItemService.findRetailItemById(orderRequestBody.getId());
        if(!retailItem.isPresent()){
            throw new IllegalStateException("retail item id error/not found");
        }

        OrderEntity orderEntity = new OrderEntity(orderRequestBody.getNum(), retailItem.get());
        orderEntityRepository.save(orderEntity);
        shoppingCart.getProducts().add(orderEntity);

        return "Done";
    }

    @Override
    @Transactional
    public String addToCartMultiple(List<OrderRequestBody> orders){
        for(OrderRequestBody body: orders){
            this.addToCart(body);
        }

        return "Done";
    }




}
