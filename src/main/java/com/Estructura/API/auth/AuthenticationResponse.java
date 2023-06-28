package com.Estructura.API.auth;

import com.Estructura.API.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
