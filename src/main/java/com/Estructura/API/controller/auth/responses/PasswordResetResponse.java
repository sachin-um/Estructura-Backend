package com.Estructura.API.controller.auth.responses;

import com.Estructura.API.config.validation.ValidatedResponse;
import com.Estructura.API.controller.auth.requests.PasswordResetRequest;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetResponse extends ValidatedResponse<PasswordResetRequest>{
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
}
