package com.Estructura.API.service;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Response;
import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.repository.ResponseRepository;
import com.Estructura.API.requests.customerRequests.CustomerRequestRequest;
import com.Estructura.API.requests.serviceProviderResponses.ResponseRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
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

@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService{
    private final ResponseRepository responseRepository;
    private final ServiceProviderService serviceProviderService;
    private final CustomerRequestService customerRequestService;


    @Override
    public GenericAddOrUpdateResponse<ResponseRequest> addResponse(
        ResponseRequest responseRequest) throws IOException {
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
                    .build();
                saveImagesOrDocuments(responseRequest,serviceProviderResponse);
                Response savedResponse=
                    responseRepository.save(serviceProviderResponse);
                String uploadDir =
                    "./files/responses-files/" + savedResponse.getServiceProvider().getId() + "/" +
                    savedResponse.getId();
                FileUploadUtil.uploadDocuments(uploadDir,
                    responseRequest.getDocuments(),
                    savedResponse.getDocument1Name(),
                    savedResponse.getDocument2Name(),savedResponse.getDocument3Name());
                response.setSuccess(true);
                response.setId(savedResponse.getId());
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
                    if (count == 0) response.setDocument1(documentName);
                    response.setDocument1Name(FileUploadUtil.generateFileName(
                        documentName));//check the image count is less than 3
                    if (count == 1) response.setDocument2(documentName);
                    response.setDocument2Name(
                        FileUploadUtil.generateFileName(documentName));
                    if (count == 2) response.setDocument3(documentName);
                    response.setDocument3Name(
                        FileUploadUtil.generateFileName(documentName));
                    count++;
                }
            }
        }
    }

}
