package com.Estructura.API.responses;


import com.Estructura.API.model.CustomerRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class customerRequestResponse {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;

    @JsonProperty("error_message")
    private String errormessage;

    @JsonProperty("customerRequest")
    private CustomerRequest customerRequest;
}
