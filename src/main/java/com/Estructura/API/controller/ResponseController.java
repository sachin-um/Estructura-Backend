package com.Estructura.API.controller;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Response;
import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.requests.serviceProviderResponses.ResponseRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.service.CustomerRequestService;
import com.Estructura.API.service.ResponseService;
import com.Estructura.API.service.ServiceProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/response")
public class ResponseController {
    private final ResponseService responseService;
    private final ServiceProviderService serviceProviderService;
    private final CustomerRequestService customerRequestService;

    @PostMapping("/add")
    public GenericAddOrUpdateResponse<ResponseRequest> addResponse(@ModelAttribute ResponseRequest responseRequest) throws IOException {
        return responseService.addResponse(responseRequest);
    }

    @GetMapping("all/service-provider/{providerId}")
    public ResponseEntity<List<Response>> getAllResponsesByProvider(@PathVariable("providerId") Integer id){
        Optional<ServiceProvider> serviceProvider=
            serviceProviderService.findById(id);
        if (serviceProvider.isPresent()){
            return responseService.getResponsesByProvider(serviceProvider.get());
        }else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("all/request/{requestId}")
    public ResponseEntity<List<Response>> getAllResponseByRequest(@PathVariable("requestId") Long requestId){
        Optional<CustomerRequest> customerRequest=
            customerRequestService.getCustomerRequestById(requestId);
        if (customerRequest.isPresent()){
            return responseService.getResponsesByRequest(customerRequest.get());
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getResponseById(@PathVariable("id") Long id){
        return responseService.getResponseById(id);
    }

    @DeleteMapping("delete/{id}")
    public GenericDeleteResponse<Long> deleteResponse(@PathVariable("id") Long id){
        GenericDeleteResponse<Long> response=new GenericDeleteResponse<>();
        ResponseEntity<Response> existedResponse=
            responseService.getResponseById(id);
        if (existedResponse.getStatusCode().is2xxSuccessful()){
            return responseService.deleteResponse(existedResponse.getBody());
        }else {
            response.setSuccess(false);
            return response;
        }
    }

}
