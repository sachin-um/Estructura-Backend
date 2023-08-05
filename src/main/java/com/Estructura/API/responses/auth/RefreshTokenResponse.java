package com.Estructura.API.responses.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class RefreshTokenResponse {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("error_message")
    private String message;
    @JsonProperty("access_token")
    private String access_token;
    @JsonProperty("refresh_token")
    private String refresh_token;
}