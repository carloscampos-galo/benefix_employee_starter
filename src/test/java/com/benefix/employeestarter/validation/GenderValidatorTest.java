package com.benefix.employeestarter.validation;

import static org.junit.jupiter.api.Assertions.*;

import com.benefix.employeestarter.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenderValidatorTest {

  private GenderValidator validator;

  @BeforeEach
  void setUp() {
    validator = new GenderValidator();
  }

  @Test
  void isValid_returnsTrue_whenGenderIsMale() {
    assertTrue(validator.isValid(Gender.M, null));
  }

  @Test
  void isValid_returnsTrue_whenGenderIsFemale() {
    assertTrue(validator.isValid(Gender.F, null));
  }

  @Test
  void isValid_returnsFalse_whenGenderIsNull() {
    assertFalse(validator.isValid(null, null));
  }
}
