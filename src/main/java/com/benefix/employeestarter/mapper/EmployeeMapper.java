package com.benefix.employeestarter.mapper;

import com.benefix.employeestarter.dto.request.CreateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.request.UpdateEmployeeRequestDTO;
import com.benefix.employeestarter.dto.response.EmployeeResponseDTO;
import com.benefix.employeestarter.entity.EmployeeEntity;
import com.benefix.employeestarter.entity.embeddable.AddressEmbeddable;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  public EmployeeEntity toEntity(CreateEmployeeRequestDTO request) {
    return EmployeeEntity.builder()
        .title(request.title())
        .firstName(request.firstName())
        .surname(request.surname())
        .dateOfBirth(request.dateOfBirth())
        .gender(request.gender())
        .email(request.email())
        .address(
            toAddressEmbeddable(
                request.street(), request.city(), request.postcode(), request.country()))
        .build();
  }

  public EmployeeEntity.Builder toBuilder(UpdateEmployeeRequestDTO request) {
    return EmployeeEntity.builder()
        .title(request.title())
        .firstName(request.firstName())
        .surname(request.surname())
        .dateOfBirth(request.dateOfBirth())
        .gender(request.gender())
        .email(request.email())
        .address(
            toAddressEmbeddable(
                request.street(), request.city(), request.postcode(), request.country()));
  }

  public EmployeeResponseDTO toResponse(EmployeeEntity entity) {
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

  private AddressEmbeddable toAddressEmbeddable(
      String street, String city, String postcode, String country) {
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
