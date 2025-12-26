package com.benefix.employeestarter.dto.response;

import com.benefix.employeestarter.enums.Gender;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public record EmployeeResponseDTO(
    Long id,
    String employeeNo,
    String title,
    String firstName,
    String surname,
    LocalDate dateOfBirth,
    Gender gender,
    String email,
    String street,
    String city,
    String postcode,
    String country,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt) {}
