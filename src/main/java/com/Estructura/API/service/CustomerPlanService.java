package com.Estructura.API.service;


import com.Estructura.API.model.CustomerPlan;
import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.requests.customerPlans.CustomerPlanRequest;
import com.Estructura.API.responses.CustomerPlan.CustomerPlanResponse;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface CustomerPlanService {
    CustomerPlanResponse saveCustomerPlan(@ModelAttribute CustomerPlanRequest customerPlanRequest);

    ResponseEntity<CustomerPlan> getCustomerPlanbyId(Long id);

    ResponseEntity<List<CustomerPlan>> getCustomerPlansByCustomer(Integer id);
}
