package com.benefix.employeestarter.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AddressEmbeddable {

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "city")
  private String city;

  @Column(name = "postcode")
  private String postcode;

  @Column(name = "country", nullable = false)
  private String country;

  protected AddressEmbeddable() {}

  private AddressEmbeddable(Builder builder) {
    this.update(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  public Builder toBuilder() {
    return new Builder()
        .street(this.street)
        .city(this.city)
        .postcode(this.postcode)
        .country(this.country);
  }

  public void update(Builder builder) {
    this.street = builder.street;
    this.city = builder.city;
    this.postcode = builder.postcode;
    this.country = builder.country;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getPostcode() {
    return postcode;
  }

  public String getCountry() {
    return country;
  }

  public static class Builder {
    private String street;
    private String city;
    private String postcode;
    private String country;

    private Builder() {}

    public Builder street(String street) {
      this.street = street;
      return this;
    }

    public Builder city(String city) {
      this.city = city;
      return this;
    }

    public Builder postcode(String postcode) {
      this.postcode = postcode;
      return this;
    }

    public Builder country(String country) {
      this.country = country;
      return this;
    }

    public AddressEmbeddable build() {
      return new AddressEmbeddable(this);
    }
  }
}
