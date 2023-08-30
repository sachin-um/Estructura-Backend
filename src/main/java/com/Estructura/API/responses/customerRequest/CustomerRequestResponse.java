package com.Estructura.API.responses.customerRequest;

import com.Estructura.API.model.CustomerRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestResponse extends ValidatedResponse<CustomerRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("customerRequest")
    private CustomerRequest customerRequest;

}
