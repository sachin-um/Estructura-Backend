package com.Estructura.API.service;

import com.Estructura.API.requests.cart.CartRequest;
import com.Estructura.API.requests.projects.ProjectRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

public interface CartService {
    GenericAddOrUpdateResponse<CartRequest> saveOrUpdateCart(
        @ModelAttribute CartRequest cartRequest) throws IOException;

}
