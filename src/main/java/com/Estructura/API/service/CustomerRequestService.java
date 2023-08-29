package com.Estructura.API.service;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Role;
import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;

public interface CustomerRequestService {
    GenericAddOrUpdateResponse<CustomerRequestRequest> saveCustomerRequest(@ModelAttribute
    CustomerRequestRequest customerRequestRequest) throws IOException;

    ResponseEntity<List<CustomerRequest>> fetchCustomerRequestByRole(Role role);
}
