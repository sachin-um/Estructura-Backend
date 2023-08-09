package com.Estructura.API.responses.retailItems;

import com.Estructura.API.model.RetailItem;
import com.Estructura.API.requests.retailItems.RetailItemRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetailItemResponse extends ValidatedResponse<RetailItemRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("retailItem")
    private RetailItem retailItem;
}
