package com.Estructura.API.responses.cart;

import com.Estructura.API.requests.cart.CheckOutRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutResponse extends ValidatedResponse<CheckOutRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
}
