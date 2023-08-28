package com.Estructura.API.service;

import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;

public interface CustomerRequestService {
    GenericAddOrUpdateResponse<CustomerRequestRequest> saveCustomerRequest(@ModelAttribute
    CustomerRequestRequest customerRequestRequest) throws IOException;
}
