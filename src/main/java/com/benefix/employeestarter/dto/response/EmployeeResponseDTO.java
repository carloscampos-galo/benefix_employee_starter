package com.benefix.employeestarter.dto.response;

import com.benefix.employeestarter.entity.EmployeeEntity;
import com.benefix.employeestarter.entity.embeddable.AddressEmbeddable;
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
    ZonedDateTime updatedAt) {

  public static EmployeeResponseDTO from(EmployeeEntity entity) {
    AddressEmbeddable address = entity.getAddress();
    return new EmployeeResponseDTO(
        entity.getId(),
        entity.getEmployeeNo(),
        entity.getTitle(),
        entity.getFirstName(),
        entity.getSurname(),
        entity.getDateOfBirth(),
        entity.getGender(),
        entity.getEmail(),
        address != null ? address.getStreet() : null,
        address != null ? address.getCity() : null,
        address != null ? address.getPostcode() : null,
        address != null ? address.getCountry() : null,
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }
}
