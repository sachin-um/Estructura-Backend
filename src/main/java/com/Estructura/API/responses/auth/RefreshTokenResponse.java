package com.Estructura.API.responses.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class RefreshTokenResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("errorMessage")
    private String message;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
}