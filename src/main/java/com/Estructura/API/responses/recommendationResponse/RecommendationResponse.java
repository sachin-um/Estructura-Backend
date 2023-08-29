package com.Estructura.API.responses.recommendationResponse;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationResponse extends ValidatedResponse<RecommendationRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success=false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("retailItems")
    private List<RetailItem> retailItems;

    @JsonProperty("professionals")
    private List<Professional> professionals;
}
