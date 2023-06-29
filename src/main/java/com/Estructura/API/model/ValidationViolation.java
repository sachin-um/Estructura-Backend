package com.Estructura.API.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationViolation {
    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private String message;
}
