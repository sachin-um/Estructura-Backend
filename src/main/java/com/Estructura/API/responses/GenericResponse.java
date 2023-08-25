package com.Estructura.API.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<RequestClass> extends ValidatedResponse<RequestClass> {
  @JsonProperty("success")
  private boolean success;
  @JsonProperty("message")
  private String message;
}
