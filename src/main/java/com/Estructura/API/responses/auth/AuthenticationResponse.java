package com.Estructura.API.responses.auth;

import com.Estructura.API.model.Role;
import com.Estructura.API.model.ServiceProviderType;
import com.Estructura.API.requests.auth.AuthenticationRequest;
import com.Estructura.API.responses.ValidatedResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.Builder.Default;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse extends ValidatedResponse<AuthenticationRequest> {
  // Auth response fields
  @JsonProperty("success")
  @Default
  private boolean success = false;
  @JsonProperty("accessToken")
  private String accessToken;
  @JsonProperty("refreshToken")
  private String refreshToken;
  // User State fields
  @JsonProperty("id")
  private Integer id;
  @JsonProperty("firstName")
  private String firstName;
  @JsonProperty("lastName")
  private String lastName;
  @JsonProperty("email")
  private String email;
  @JsonProperty("role")
  private Role role;
  @JsonProperty("profileImage")
  private String profileImage;
  @JsonProperty("profileImageName")
  private String profileImageName;
  @JsonProperty("serviceProviderType")
  private ServiceProviderType serviceProviderType;
}


/*
export interface UserState {
  profileImage: null | string;
  profileImageName: null | string;
  email: string;
  firstName: string;
  id: number;
  lastName: string;
  role: Role;
}

export interface SignInRequest {
  email: string;
  password: string;
}

export interface AuthenticationResponse extends ValidatedResponse, UserState {
  accessToken: null | string;
  refreshToken: null | string;
  success: boolean;
}
*/