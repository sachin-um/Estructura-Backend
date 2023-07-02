package com.Estructura.API.controller.auth.responses;

import com.Estructura.API.config.validation.ValidatedResponse;
import com.Estructura.API.controller.auth.requests.RegisterRequest;
import com.Estructura.API.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse extends ValidatedResponse<RegisterRequest>{
    @JsonProperty("success")
    @Default
    private boolean success = false;
    @JsonProperty("error_message")
    private String errormessage;
    @JsonProperty("logged_user")
    private User loggedUser;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

}
