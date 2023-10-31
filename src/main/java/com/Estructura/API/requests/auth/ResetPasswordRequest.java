package com.Estructura.API.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank(message = "Both Password fields are required")
    private String password;
    @NotBlank(message = "Both Password fields are required")
    private String confirmPassword;
}
