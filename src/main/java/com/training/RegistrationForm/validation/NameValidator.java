package com.training.RegistrationForm.validation;

import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@NoArgsConstructor
public class NameValidator implements ConstraintValidator<ValidName, String> {

    public void initialize(com.training.RegistrationForm.validation.ValidName constraint) {
    }

    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name.matches("([A-Z])([a-z]+)|([А-ЩЬ-ЯІЇ])([а-щь-яії]+)");
    }

}

