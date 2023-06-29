package com.Estructura.API.config.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;

// A subclass of ValidatedResponse that is used to return validation errors
@AllArgsConstructor
public class ValidationViolation {
    @JsonProperty("field")
    private String field;

    @JsonProperty("message")
    private String message;
}
