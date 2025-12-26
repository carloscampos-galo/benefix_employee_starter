package com.benefix.employeestarter.exception;

import java.util.function.Supplier;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(Long id) {
    super(String.format("Employee not found with id: %d", id));
  }

  public EmployeeNotFoundException(String employeeNo) {
    super(String.format("Employee not found with employeeNo: %s", employeeNo));
  }

  public static Supplier<EmployeeNotFoundException> withId(Long id) {
    return () -> new EmployeeNotFoundException(id);
  }

  public static Supplier<EmployeeNotFoundException> withEmployeeNo(String employeeNo) {
    return () -> new EmployeeNotFoundException(employeeNo);
  }
}
