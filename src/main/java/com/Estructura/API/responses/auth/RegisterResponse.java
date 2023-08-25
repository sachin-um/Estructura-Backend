package com.Estructura.API.responses.auth;

import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.Builder.Default;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse extends ValidatedResponse<RegisterRequest> {
    @JsonProperty("success")
    @Default
    private boolean success = false;
    @JsonProperty("errorMessage")
    private String errorMessage;
    @JsonProperty("loggedUser")
    private User loggedUser;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
}
