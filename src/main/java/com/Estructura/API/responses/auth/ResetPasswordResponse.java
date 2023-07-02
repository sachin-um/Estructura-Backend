package com.Estructura.API.responses.auth;

import com.Estructura.API.config.validation.ValidatedResponse;
import com.Estructura.API.requests.auth.ResetPasswordRequest;
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
public class ResetPasswordResponse extends ValidatedResponse<ResetPasswordRequest>{
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
}
