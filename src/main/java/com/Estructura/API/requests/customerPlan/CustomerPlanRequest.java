package com.Estructura.API.requests.customerPlan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPlanRequest {
    private String planName;
    private Integer coverImageId;
    private Integer userID;
    private Integer[] professionals;
    private Long[] retailItems;
    private Long[] rentingItems;
}
