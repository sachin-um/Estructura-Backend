package com.Estructura.API.service;

import com.Estructura.API.requests.customerPlan.CustomerPlanRequest;
import com.Estructura.API.responses.CustomerPlan.CustomerPlanResponse;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface CustomerPlanService {
    CustomerPlanResponse saveCustomerPlan(@ModelAttribute CustomerPlanRequest customerPlanRequest);
}
