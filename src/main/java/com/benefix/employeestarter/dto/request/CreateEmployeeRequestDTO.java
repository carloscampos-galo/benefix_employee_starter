package com.benefix.employeestarter.dto.request;

import com.benefix.employeestarter.entity.embeddable.AddressEmbeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateEmployeeRequestDTO(
    @NotBlank(message = "Title is required") String title,
    @NotBlank(message = "Firstname is required") String firstName,
    @NotBlank(message = "Surname is required") String surname,
    @NotNull(message = "Date of birth is required") LocalDate dateOfBirth,
    @NotBlank(message = "Gender is required") String gender,
    @NotBlank(message = "Email is required") @Email(message = "Invalid email") String email,
    @NotBlank(message = "Street is required") String street,
    String city,
    String postcode,
    @NotBlank(message = "Country is required") String country) {

  public AddressEmbeddable toAddressEmbeddable() {
    if (street == null && city == null && postcode == null && country == null) {
      return null;
    }
    return AddressEmbeddable.builder()
        .street(street)
        .city(city)
        .postcode(postcode)
        .country(country)
        .build();
  }
}
