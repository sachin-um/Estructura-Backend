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
    // Auth response fields
    @JsonProperty("success")
    @Default
    private boolean success = false;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    // User State fields
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Role role;
    @JsonProperty("ProfileImage")
    private String profileImage;
    @JsonProperty("ProfileImageName")
    private String profileImageName;
}


/*
export interface UserState {
  ProfileImage: null | string;
  ProfileImageName: null | string;
  email: string;
  firstname: string;
  id: number;
  lastname: string;
  role: Role;
}

export interface SignInRequest {
  email: string;
  password: string;
}

export interface AuthenticationResponse extends ValidatedResponse, UserState {
  access_token: null | string;
  refresh_token: null | string;
  success: boolean;
}
*/