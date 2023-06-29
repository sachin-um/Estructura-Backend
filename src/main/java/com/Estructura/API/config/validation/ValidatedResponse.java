package com.Estructura.API.config.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Extend this class to include a list of validation violations in your response
 * Validations must be included in the entity class using jakarta.validation annotations
 * Then before returning the response, call setValidationViolationsFromConstraintViolations
 * to populate the list of validation violations from the set of constraint violations
 * 
 * example:
 *      // RESPONSE is the class that extends ValidatedResponse
 *      // ENTITY is the entity that is being validated
 *      ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
 *      Validator validator = factory.getValidator();
 *      var violations = validator.validate(ENTITY);
 *      if (!violations.isEmpty()){
 *          var response = RESPONSE.builder()
 *                  .build();
 *          response.setValidationViolationsFromConstraintViolations(violations);
 *          return response;
 *      }
 * ! NOTE: This class is not meant to be used directly, but rather extended
 *   Make sure that the extended class has it's own Builder, Getter and Setter annotations 
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ValidatedResponse<entity> {
    @JsonProperty("validation_violations")
    public List<ValidationViolation> validationViolations = new java.util.ArrayList<>();

    public void setValidationViolationsFromConstraintViolations(Set<ConstraintViolation<entity>> constraintViolations) {
        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            for (ConstraintViolation<?> violation : constraintViolations) {
                addError(violation);
            }
        }
    }

    private boolean addError(ConstraintViolation<?> violation) {
        return validationViolations.add(
                new ValidationViolation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()));
    }

    public void addError(String field, String message) {
        validationViolations.add(new ValidationViolation(field, message));
    }

    public boolean hasErrors() {
        return !validationViolations.isEmpty();
    }

    public boolean checkValidity(entity entity) {
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            setValidationViolationsFromConstraintViolations(violations);
        }
        return !hasErrors();
    }
}
