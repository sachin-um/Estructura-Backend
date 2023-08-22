package com.Estructura.API.responses.recommendationResponse;

import com.Estructura.API.model.Professional;
import com.Estructura.API.model.RetailItem;
import com.Estructura.API.requests.recommendationRequests.RecommendationRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class RecommendationResponse extends ValidatedResponse<RecommendationRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success=false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("retailItems")
    private RetailItem[] retailItems;

    @JsonProperty("professionals")
    private Professional[] professionals;
}
