package com.benefix.employeestarter.validation;

import com.benefix.employeestarter.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, Gender> {

  @Override
  public boolean isValid(Gender gender, ConstraintValidatorContext context) {
    return gender != null;
  }
}
