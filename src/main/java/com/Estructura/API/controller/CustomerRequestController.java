package com.Estructura.API.controller;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Role;
import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.service.CustomerRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer-requests")
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<CustomerRequestRequest> addCustomerRequest(@ModelAttribute CustomerRequestRequest customerRequestRequest) throws IOException {
        return customerRequestService.saveCustomerRequest(customerRequestRequest);
    }
//
//    @GetMapping("/all/{role]")
//    public ResponseEntity<List<CustomerRequest>> getCustomerRequestByRole(@PathVariable("role")
//    Role role){
//
//    }

}
