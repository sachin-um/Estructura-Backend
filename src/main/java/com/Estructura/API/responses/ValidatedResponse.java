package com.Estructura.API.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/*
 * Extend this class to include a list of validation violations in your response
 * Validations must be included in the entity class using jakarta.validation
 * annotations
 * Then before returning the response, call
 * setValidationViolationsFromConstraintViolations
 * to populate the list of validation violations from the set of constraint
 * violations
 *
 * example:
 *      // RESPONSE is the class that extends ValidatedResponse
 *      // ENTITY is the entity that is being validated
 *      var ENTITY= ENTITY.builder()
 *              .x(request.getblah())
 *              .y(request.getblahblah())
 *              .build();
 *      var response = new RESPONSE();
 *
 *      // Pre check fields that aren't checked by response.checkValidity()
 *      if(request.getBlah().isEmpty()) {
 *          response.addError("blah", "blah is required");
 *      }
 *
 *      // If Request is valid
 *      if (response.checkValidity(user)){
 *          response.setblahblahblah(true);
 *      }
 *
 *      return response;
 * ! NOTE: This class is not meant to be used directly, but rather extended
 *   Make sure that the extended class has its own Builder, Getter and
 * Setter annotations
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ValidatedResponse<entity> {
    @JsonProperty("validation_violations")
    public List<ValidationViolation> validationViolations =
        new java.util.ArrayList<>();

    public void setValidationViolationsFromConstraintViolations(
        Set<ConstraintViolation<entity>> constraintViolations) {
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<?> violation : constraintViolations) {
                addError(violation);
            }
        }
    }

    private void addError(ConstraintViolation<?> violation) {
        validationViolations.add(
            new ValidationViolation(violation.getPropertyPath().toString(),
                                    violation.getMessage()
            ));
    }

    public void addError(String field, String message) {
        validationViolations.add(new ValidationViolation(field, message));
    }

    public boolean hasErrors() {
        return !validationViolations.isEmpty();
    }

    public boolean checkValidity(entity entity) {
        var factory    = Validation.buildDefaultValidatorFactory();
        var validator  = factory.getValidator();
        var violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            setValidationViolationsFromConstraintViolations(violations);
        }
        return !hasErrors();
    }
}
