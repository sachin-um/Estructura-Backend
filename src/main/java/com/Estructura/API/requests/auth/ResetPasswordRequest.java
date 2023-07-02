package com.Estructura.API.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Both Password fields are required")
    private String newPassword;
    @NotBlank(message = "Both Password fields are required")
    private String confirmPassword;
}
