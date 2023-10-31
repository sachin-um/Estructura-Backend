package com.Estructura.API.responses.cart;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String userid;
    private double amount;
    private String merchant_id;
    private String order_id;
    private String currency;
    private String hash;
}
