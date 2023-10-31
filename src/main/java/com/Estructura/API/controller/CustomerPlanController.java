package com.Estructura.API.controller;

import com.Estructura.API.model.CustomerPlan;
import com.Estructura.API.requests.customerPlans.CustomerPlanRequest;
import com.Estructura.API.responses.CustomerPlan.CustomerPlanResponse;
import com.Estructura.API.service.CustomerPlanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer-plan")
public class CustomerPlanController {
    private final CustomerPlanService customerPlanService;
    @PostMapping("/create")
    public CustomerPlanResponse savePlan(@ModelAttribute CustomerPlanRequest customerPlanRequest){
        return customerPlanService.saveCustomerPlan(customerPlanRequest);
    }

    @GetMapping("/plan/{id}")
    public ResponseEntity<CustomerPlan> getCustomerPlan(@PathVariable("id") Long id){
        return customerPlanService.getCustomerPlanbyId(id);
    }

    @GetMapping("/plan/user/{id}")
    public ResponseEntity<List<CustomerPlan>> getAllCustomerPlanByCustomer(@PathVariable("id") Integer id){
        return customerPlanService.getCustomerPlansByCustomer(id);
    }
}
