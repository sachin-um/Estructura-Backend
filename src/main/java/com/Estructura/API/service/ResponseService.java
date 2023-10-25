package com.Estructura.API.service;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.model.Response;
import com.Estructura.API.model.ServiceProvider;
import com.Estructura.API.requests.serviceProviderResponses.ActionRequest;
import com.Estructura.API.requests.serviceProviderResponses.ResponseRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericDeleteResponse;
import com.Estructura.API.responses.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ResponseService {
    GenericAddOrUpdateResponse<ResponseRequest> addResponse(
        @ModelAttribute ResponseRequest responseRequest
    ) throws IOException;

    ResponseEntity<Response> getResponseById(Long id);

    ResponseEntity<List<Response>> getResponsesByProvider(ServiceProvider serviceProvider);

    ResponseEntity<List<Response>> getResponsesByRequest(CustomerRequest customerRequest);

    Optional<Response> fetchResponseById(Long id);

    GenericResponse<Long> acceptOrDeclineResponse(ActionRequest actionRequest);
    GenericDeleteResponse<Long> deleteResponse(Response response);
}
