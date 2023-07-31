package com.Estructura.API.responses;

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
public class GenericResponse<RequestClass> extends ValidatedResponse<RequestClass> {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
}
// long bool etc keepayak oone
