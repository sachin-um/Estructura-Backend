package com.Estructura.API.auth;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequest extends RegisterRequest{
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
}
