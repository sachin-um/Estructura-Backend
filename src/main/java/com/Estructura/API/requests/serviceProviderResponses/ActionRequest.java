package com.Estructura.API.requests.serviceProviderResponses;

import com.Estructura.API.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionRequest {
    private Integer customer_id;
    private Long response_id;

    private ResponseStatus action;
}
