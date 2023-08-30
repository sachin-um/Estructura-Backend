package com.Estructura.API.controller;

import com.Estructura.API.model.Customer;
import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Role;
import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.CustomerRequestService;
import com.Estructura.API.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer-requests")
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;
    private final CustomerService customerService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<CustomerRequestRequest> addCustomerRequest(@ModelAttribute CustomerRequestRequest customerRequestRequest) throws IOException {
        return customerRequestService.saveCustomerRequest(customerRequestRequest);
    }

    @GetMapping("/all/{role}")
    public ResponseEntity<List<CustomerRequest>> getCustomerRequestByRole(@PathVariable("role")
    Role role){
        return customerRequestService.fetchCustomerRequestByRole(role);
    }

    @GetMapping("/customer-request/{id}")
    public ResponseEntity<CustomerRequest> getCustomerRequestById(@PathVariable("id") Long id){
        return customerRequestService.fetchCustomerRequestById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerRequest>> getAllCustomerRequest(){
        return customerRequestService.fetchAllCustomerRequest();
    }

    @GetMapping("/all-by-customer/{customer_id}")
    public ResponseEntity<List<CustomerRequest>> getCustomerRequestByCustomer(@PathVariable("customer_id") int customer_id){
        Optional<Customer> customer=customerService.findById(customer_id);
        if (customer.isPresent()){
            return customerRequestService.fetchAllCustomerRequestByCustomer(customer.get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public GenericDeleteResponse<Long> deleteCustomerRequest(@PathVariable(
        "id") Long id){
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        ResponseEntity<CustomerRequest> request=
            customerRequestService.fetchCustomerRequestById(id);
        if (request.getStatusCode().is2xxSuccessful()){
            return customerRequestService.deleteCustomerRequest(request.getBody());
        }else {
            response.setSuccess(false);
            return response;
        }
    }

}
