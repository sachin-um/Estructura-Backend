package com.Estructura.API.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;


@Builder
@AllArgsConstructor
public class AdminDemoResponse {
    // TODO: Remove this class
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("message")
    private String message;
}
