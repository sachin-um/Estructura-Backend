package com.Estructura.API.responses.rentingItems;

import com.Estructura.API.model.RentingItem;
import com.Estructura.API.requests.rentingItems.RentingItemRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentingItemResponse extends ValidatedResponse<RentingItemRequest> {
    @JsonProperty("success")
    @Builder.Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("rentingItem")
    private RentingItem rentingItem;
}
