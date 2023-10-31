package com.Estructura.API.service;

import com.Estructura.API.requests.cart.PaymentDetailsRequest;
import com.Estructura.API.responses.cart.PaymentResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {
    PaymentResponse getPaymentDetails(@RequestBody PaymentDetailsRequest paymentDetailsRequest);


}
