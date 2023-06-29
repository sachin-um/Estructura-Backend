package com.Estructura.API.auth;

import java.util.List;
import java.util.Set;

import com.Estructura.API.model.User;
import com.Estructura.API.model.ValidationViolation;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("logged_user")
    private User loggedUser;
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("validation_violations")
    @Default
    private List<ValidationViolation> validationViolations = new java.util.ArrayList<>();

    public void setValidationViolationsFromConstraintViolations(Set<ConstraintViolation<User>>constraintViolations) {
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<?> violation : constraintViolations) {
                addError(violation);
            }
        }
    }
    private boolean addError(ConstraintViolation<?> violation) {
        return validationViolations.add(
            new ValidationViolation(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            )
        );
    }
}
