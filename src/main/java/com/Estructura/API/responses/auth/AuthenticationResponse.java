package com.Estructura.API.responses.auth;

import com.Estructura.API.model.Role;
import com.Estructura.API.requests.auth.AuthenticationRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse extends ValidatedResponse<AuthenticationRequest>{
    @JsonProperty("success")
    @Default
    private boolean success = false;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
