package com.Estructura.API.requests.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetailsRequest {
    private int customerId;
    private double amount;
    private long retailStoreId;

}
