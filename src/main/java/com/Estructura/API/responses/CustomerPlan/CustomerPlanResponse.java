package com.Estructura.API.responses.CustomerPlan;

import com.Estructura.API.model.CustomerPlan;
import com.Estructura.API.requests.customerPlans.CustomerPlanRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanResponse extends ValidatedResponse<CustomerPlanRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("id")
    private Long id;
}
