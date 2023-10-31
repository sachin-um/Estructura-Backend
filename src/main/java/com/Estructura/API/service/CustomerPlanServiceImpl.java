package com.Estructura.API.service;


import com.Estructura.API.model.*;
import com.Estructura.API.repository.CustomerPlanProfessionalsRepository;
import com.Estructura.API.repository.CustomerPlanRentingItemRepository;
import com.Estructura.API.repository.CustomerPlanRepository;
import com.Estructura.API.repository.CustomerPlanRetailIItemRepository;
import com.Estructura.API.requests.customerPlans.CustomerPlanRequest;
import com.Estructura.API.responses.CustomerPlan.CustomerPlanResponse;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final CustomerPlanRentingItemRepository customerPlanRentingItemRepository;
    @Override
    public CustomerPlanResponse saveCustomerPlan(
        CustomerPlanRequest customerPlanRequest) {
        CustomerPlanResponse customerPlanResponse=new CustomerPlanResponse();
        if (customerPlanResponse.checkValidity(customerPlanRequest)){
            Optional<Customer> customer=
                customerService.findById(customerPlanRequest.getUserID());
            if (customer.isPresent()){
                CustomerPlan customerPlan=CustomerPlan.builder()
                    .planName(customerPlanRequest.getName())
                    .createdBy(customerPlanRequest.getUserID())
                    .coverImageId(customerPlanRequest.getCoverImageId())
                    .customer(customer.get())
                    .build();
                CustomerPlan savedPlan=
                    customerPlanRepository.save(customerPlan);
                if (customerPlanRequest.getProfessionals().length !=0){
                    Arrays.stream(customerPlanRequest.getProfessionals()).forEach(professional->{
                        var planProfessional=
                            CustomerPlanProfessionals.builder()
                            .professionalId(professional)
                                                     .customerPlan(savedPlan)
                                .build();
                        customerPlanProfessionalsRepository.save(planProfessional);

                    });
                }
                if (customerPlanRequest.getRentingItems().length !=0){
                    Arrays.stream(customerPlanRequest.getRentingItems()).forEach(rentingItem->{
                        var planRentingItem= CustomerPlanRentingItems.builder()
                            .rentingItemId(rentingItem)
                            .customerPlan(savedPlan)
                            .build();
                        customerPlanRentingItemRepository.save(planRentingItem);
                    });
                }
                if (customerPlanRequest.getRetailItems().length !=0){
                    Arrays.stream(customerPlanRequest.getRetailItems()).forEach(retailItem->{
                        var planRetailItem= CustomerPlanRetailItems.builder()
                            .retailItems(retailItem)
                            .customerPlan(savedPlan)
                            .build();
                        customerPlanRetailIItemRepository.save(planRetailItem);
                    });
                }
                customerPlanResponse.setSuccess(true);
                customerPlanResponse.setCustomerPlan(savedPlan);
            }else {
                customerPlanResponse.setErrormessage("Access denied");
            }

        } else {
            customerPlanResponse.setErrormessage("Bad Request");
        }
        return customerPlanResponse;
    }

    @Override
    public ResponseEntity<CustomerPlan> getCustomerPlanbyId(Long id) {
        Optional<CustomerPlan> customerPlan=customerPlanRepository.findById(id);
        return customerPlan.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
