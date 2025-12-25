package com.benefix.employeestarter.dto.request;

import com.benefix.employeestarter.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateEmployeeRequestDTO(
    @NotBlank(message = "Title is required") String title,
    @NotBlank(message = "Firstname is required") String firstName,
    @NotBlank(message = "Surname is required") String surname,
    @NotNull(message = "Date of birth is required") LocalDate dateOfBirth,
    @NotNull(message = "Gender is required") Gender gender,
    @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
    @NotBlank(message = "Street is required") String street,
    String city,
    String postcode,
    @NotBlank(message = "Country is required") String country) {}
