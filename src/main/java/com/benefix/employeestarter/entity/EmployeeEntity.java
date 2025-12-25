package com.benefix.employeestarter.entity;

import com.benefix.employeestarter.entity.embeddable.AddressEmbeddable;
import com.benefix.employeestarter.entity.generator.GeneratedEmployeeNo;
import com.benefix.employeestarter.enums.Gender;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "employee")
@EntityListeners(AuditingEntityListener.class)
@SQLRestriction("is_deleted IS NOT TRUE")
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @GeneratedEmployeeNo
  @Column(name = "employee_no", unique = true, updatable = false)
  private String employeeNo;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "surname", nullable = false)
  private String surname;

  @Column(name = "date_of_birth", nullable = false)
  private LocalDate dateOfBirth;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  private Gender gender;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Embedded private AddressEmbeddable address;

  @CreatedDate
  @Column(
      name = "created_at",
      nullable = false,
      updatable = false,
      columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private ZonedDateTime updatedAt;

  @Column(name = "is_deleted")
  private Boolean isDeleted;

  protected EmployeeEntity() {}

  private EmployeeEntity(Builder builder) {
    this.update(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder()
        .title(this.title)
        .firstName(this.firstName)
        .surname(this.surname)
        .dateOfBirth(this.dateOfBirth)
        .gender(this.gender)
        .email(this.email)
        .address(this.address);
  }

  public void update(Builder builder) {
    this.title = builder.title;
    this.firstName = builder.firstName;
    this.surname = builder.surname;
    this.dateOfBirth = builder.dateOfBirth;
    this.gender = builder.gender;
    this.email = builder.email;
    this.address = builder.address;
  }

  public Long getId() {
    return id;
  }

  public String getEmployeeNo() {
    return employeeNo;
  }

  public String getTitle() {
    return title;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSurname() {
    return surname;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public Gender getGender() {
    return gender;
  }

  public String getEmail() {
    return email;
  }

  public AddressEmbeddable getAddress() {
    return address;
  }

  public ZonedDateTime getCreatedAt() {
    return createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Boolean isDeleted() {
    return isDeleted;
  }

  public void delete() {
    this.isDeleted = true;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    EmployeeEntity other = (EmployeeEntity) obj;
    return id != null && Objects.equals(id, other.id);
  }

  public static class Builder {
    private String title;
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private AddressEmbeddable address;

    private Builder() {}

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder surname(String surname) {
      this.surname = surname;
      return this;
    }

    public Builder dateOfBirth(LocalDate dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder gender(Gender gender) {
      this.gender = gender;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder address(AddressEmbeddable address) {
      this.address = address;
      return this;
    }

    public EmployeeEntity build() {
      return new EmployeeEntity(this);
    }
  }
}
