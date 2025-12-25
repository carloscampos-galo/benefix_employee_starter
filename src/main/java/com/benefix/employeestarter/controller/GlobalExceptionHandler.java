package com.benefix.employeestarter.controller;

import com.benefix.employeestarter.exception.EmailAlreadyExistsException;
import com.benefix.employeestarter.exception.EmployeeNotFoundException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              fieldErrors.put(fieldName, errorMessage);
            });
    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex) {
    if (ex.getCause() instanceof InvalidFormatException invalidFormatException
        && invalidFormatException.getTargetType().isEnum()) {
      String fieldName =
          invalidFormatException.getPath().stream()
              .map(ref -> ref.getFieldName())
              .collect(Collectors.joining("."));
      String validValues =
          Arrays.stream(invalidFormatException.getTargetType().getEnumConstants())
              .map(Object::toString)
              .collect(Collectors.joining(", "));
      Map<String, String> fieldErrors = new HashMap<>();
      fieldErrors.put(fieldName, "Must be one of: " + validValues);
      ErrorResponse error =
          new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", fieldErrors);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Malformed JSON request");
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> fieldErrors = new HashMap<>();
    ex.getConstraintViolations()
        .forEach(
            violation -> {
              String fieldName = violation.getPropertyPath().toString();
              String errorMessage = violation.getMessage();
              fieldErrors.put(fieldName, errorMessage);
            });
    ErrorResponse error =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
      DataIntegrityViolationException ex) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.CONFLICT.value(), "Database constraint violation");
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String message =
        String.format(
            "Parameter '%s' should be of type %s",
            ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParameter(
      MissingServletRequestParameterException ex) {
    String message = String.format("Required parameter '%s' is missing", ex.getParameterName());
    ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ErrorResponse> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException ex) {
    String message = String.format("HTTP method '%s' is not supported for this endpoint", ex.getMethod());
    ErrorResponse error = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), message);
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
    ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Resource not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  public record ErrorResponse(
      int status, String message, ZonedDateTime timestamp, Map<String, String> errors) {

    public ErrorResponse(int status, String message) {
      this(status, message, ZonedDateTime.now(), null);
    }

    public ErrorResponse(int status, String message, Map<String, String> errors) {
      this(status, message, ZonedDateTime.now(), errors);
    }
  }
}
