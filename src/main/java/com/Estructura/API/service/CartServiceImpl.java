package com.Estructura.API.service;

import com.Estructura.API.model.PreviousProject;
import com.Estructura.API.model.Professional;
import com.Estructura.API.repository.CartRepository;
import com.Estructura.API.repository.OrderEntityRepository;
import com.Estructura.API.requests.cart.CartRequest;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor

public class CartServiceImpl implements CartService {


    private final CartRepository cartRepository;
    private final RetailItemService retailItemService;
    private final OrderEntityRepository orderEntityRepository;

    @Override
    public GenericAddOrUpdateResponse<CartRequest> saveOrUpdateCart(
        CartRequest cartRequest) throws IOException {
        GenericAddOrUpdateResponse<CartRequest> response = new GenericAddOrUpdateResponse<>();


        return response;
    };



}
