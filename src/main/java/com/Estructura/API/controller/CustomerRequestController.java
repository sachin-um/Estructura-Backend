package com.Estructura.API.controller;

import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.service.CustomerRequestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer-requests")
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<CustomerRequestRequest> addCustomerRequest(@ModelAttribute CustomerRequestRequest customerRequestRequest) throws IOException {
        return customerRequestService.saveCustomerRequest(customerRequestRequest);
    }
    
}
