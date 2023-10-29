package com.Estructura.API.service;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.CustomerPlan;
import com.Estructura.API.repository.CustomerPlanProfessionalsRepository;
import com.Estructura.API.repository.CustomerPlanRepository;
import com.Estructura.API.repository.CustomerPlanRetailIItemRepository;
import com.Estructura.API.requests.customerPlan.CustomerPlanRequest;
import com.Estructura.API.responses.CustomerPlan.CustomerPlanResponse;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerPlanServiceImpl implements CustomerPlanService{

    private final CustomerService customerService;
    private final CustomerPlanRepository customerPlanRepository;
    private final CustomerPlanProfessionalsRepository customerPlanProfessionalsRepository;
    private final CustomerPlanRetailIItemRepository customerPlanRetailIItemRepository;
    @Override
    public CustomerPlanResponse saveCustomerPlan(
        CustomerPlanRequest customerPlanRequest) {
        CustomerPlanResponse customerPlanResponse=new CustomerPlanResponse();
        if (customerPlanResponse.checkValidity(customerPlanRequest)){
            Optional<Customer> customer=
                customerService.findById(customerPlanRequest.getUserID());
            if (customer.isPresent()){
                CustomerPlan customerPlan=CustomerPlan.builder()
                    .planName(customerPlanRequest.getPlanName())
                    .createdBy(customerPlanRequest.getUserID())
                    .coverImageId(customerPlanRequest.getCoverImageId())
                    .customer(customer.get())
                    .build();
                CustomerPlan savedPlan=
                    customerPlanRepository.save(customerPlan);
                if (customerPlanRequest.getProfessionals().length !=0){
                    Arrays.stream(customerPlanRequest.getProfessionals()).forEach(professional->{
//                        var professional=
                    });
                }
            }
        }
        return null;
    }
}
