package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.Estructura.API.repository.CustomerRequestRepository;
import com.Estructura.API.repository.ResponseRepository;
import com.Estructura.API.requests.serviceProviderResponses.ActionRequest;
import com.Estructura.API.requests.serviceProviderResponses.ResponseRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.responses.GenericResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.Estructura.API.model.CustomerRequestStatus.AWAITINGFORACCEPT;
import static com.Estructura.API.model.CustomerRequestStatus.COMPLETED;
import static com.Estructura.API.model.ResponseStatus.PENDING;

@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService{
    private final ResponseRepository responseRepository;
    private final ServiceProviderService serviceProviderService;
    private final CustomerRequestService customerRequestService;
    private final CustomerService customerService;
    private final CustomerRequestRepository customerRequestRepository;


    @Override
    public GenericAddOrUpdateResponse<ResponseRequest> addResponse(
        @ModelAttribute ResponseRequest responseRequest) throws IOException {
        GenericAddOrUpdateResponse<ResponseRequest> response=
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(responseRequest)){
            Optional<ServiceProvider> serviceProvider=
                serviceProviderService.findById(responseRequest.getServiceProviderId());
            Optional<CustomerRequest> customerRequest=
                customerRequestService.getCustomerRequestById(responseRequest.getRequestId());

            if (serviceProvider.isPresent() && customerRequest.isPresent()){
                Response serviceProviderResponse=Response.builder()
                    .createBy(responseRequest.getServiceProviderId())
                    .shortDesc(responseRequest.getShortDesc())
                    .response(responseRequest.getResponse())
                    .proposedBudget(responseRequest.getProposedBudget())
                    .customerRequest(customerRequest.get())
                    .serviceProvider(serviceProvider.get())
                    .status(PENDING)
                    .custReqId(customerRequest.get().getId())
                    .build();
                saveImagesOrDocuments(responseRequest,serviceProviderResponse);
                Response savedResponse=
                    responseRepository.save(serviceProviderResponse);
                if (savedResponse.getId()!= 0){
                    String uploadDir =
                        "./files/responses-files/" + responseRequest.getServiceProviderId() + "/" +
                        responseRequest.getRequestId();
                    FileUploadUtil.uploadDocuments(uploadDir,
                        responseRequest.getDocuments(),
                        savedResponse.getDocument1Name(),
                        savedResponse.getDocument2Name(),savedResponse.getDocument3Name());
                    CustomerRequest request=customerRequest.get();
                    request.setStatus(AWAITINGFORACCEPT);
                    customerRequestRepository.save(request);
                    response.setSuccess(true);
                    response.setId(savedResponse.getId());
                }
                else {
                    response.setSuccess(false);
                    response.setMessage("Somthing went wrong");
                }


            }else {
                response.addError("fatal","Access denied");
            }
        }else {
            response.addError("fatal","Bad Request");
        }
        return response;
    }

    @Override
    public ResponseEntity<Response> getResponseById(Long id) {
        Optional<Response> serviceProviderResponse=
            responseRepository.findById(id);
        return serviceProviderResponse.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Response>> getResponsesByProvider(
        ServiceProvider serviceProvider) {
        List<Response> responses=
            responseRepository.findByServiceProvider(serviceProvider);
        if (!responses.isEmpty()){
            return ResponseEntity.ok(responses);
        }else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public ResponseEntity<List<Response>> getResponsesByRequest(
        CustomerRequest customerRequest) {
        List<Response> responses=
            responseRepository.findByCustomerRequest(customerRequest);
        if (!responses.isEmpty()){
            return ResponseEntity.ok(responses);
        }else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public Optional<Response> fetchResponseById(Long id) {
        return responseRepository.findById(id);
    }

    @Override
    public GenericResponse<Long> acceptOrDeclineResponse(ActionRequest actionRequest) {
        GenericResponse<Long> response=new GenericResponse<>();
        Optional<Customer> requestCustomer=
            customerService.findById(actionRequest.getCustomer_id());
        Optional<Response> requestedResponse=fetchResponseById(
            actionRequest.getResponse_id());

        if (requestedResponse.isPresent() && requestCustomer.isPresent()){
            Response serviceProviderResponse=requestedResponse.get();
            Customer aqualCustomer=serviceProviderResponse.getCustomerRequest()
                                                    .getCustomer();
            CustomerRequest customerRequest=
                serviceProviderResponse.getCustomerRequest();
            if (aqualCustomer.equals(requestCustomer.get())){
                serviceProviderResponse.setStatus(actionRequest.getAction());
                customerRequest.setStatus(COMPLETED);
                Response savedResponse=
                    responseRepository.save(serviceProviderResponse);
                CustomerRequest savedRequest=
                    customerRequestRepository.save(customerRequest);
                if (savedResponse.getStatus()==actionRequest.getAction() && savedRequest.getStatus()==COMPLETED){
                    response.setSuccess(true);
                }else {
                    response.setSuccess(false);
                    response.setMessage("Something went wrong");
                }
            }else {
                response.setSuccess(false);
                response.setMessage("Bad Request");
            }
        }else {
            response.setSuccess(false);
            response.setMessage("Bad Request");
        }
        return response;
    }

    @Override
    public GenericDeleteResponse<Long> deleteResponse(Response response) {
        GenericDeleteResponse<Long> genericResponse =
            new GenericDeleteResponse<>();
        responseRepository.delete(response);
        Optional<Response> checkResponse=responseRepository.findById(
            response.getId());
        if (checkResponse.isEmpty()){
            genericResponse.setSuccess(false);
            genericResponse.setMessage("Something went wrong please try again");
        }else {
            genericResponse.setSuccess(true);
        }
        return genericResponse;
    }

    private void saveImagesOrDocuments(
        ResponseRequest responseRequest,Response response){
        int count=0;
        if (responseRequest.getDocuments() != null) {
            for (MultipartFile document : responseRequest.getDocuments()) {
                if (!document.isEmpty() &&
                    document.getOriginalFilename() != null) {
                    String documentName = StringUtils.cleanPath(
                        document.getOriginalFilename());
                    if (count == 0) {
                        response.setDocument1(documentName);
                        response.setDocument1Name(
                            FileUploadUtil.generateFileName(documentName));//check the image count is less than 3
                    }
                    if (count == 1) {
                        response.setDocument2(documentName);
                        response.setDocument2Name(FileUploadUtil.generateFileName(documentName));
                    }
                    if (count == 2) {
                        response.setDocument3(documentName);

                        response.setDocument3Name(FileUploadUtil.generateFileName(documentName));
                    }
                    count++;
                }
            }
        }
    }

}
