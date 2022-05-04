package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddressDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:28:37.037Z[GMT]")


public class AddressDTO   {
  @JsonProperty("streetName")
  private String streetName = null;

  @JsonProperty("houseNumber")
  private Integer houseNumber = null;

  @JsonProperty("postalCode")
  private String postalCode = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("country")
  private String country = null;

  public AddressDTO streetName(String streetName) {
    this.streetName = streetName;
    return this;
  }

  /**
   * Get streetName
   * @return streetName
   **/
  @Schema(example = "Clematislaan", required = true, description = "")
      @NotNull

    public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public AddressDTO houseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  /**
   * Get houseNumber
   * @return houseNumber
   **/
  @Schema(example = "69", required = true, description = "")
      @NotNull

    public Integer getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(Integer houseNumber) {
    this.houseNumber = houseNumber;
  }

  public AddressDTO postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
   **/
  @Schema(example = "2343VK", required = true, description = "")
      @NotNull

    public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public AddressDTO city(String city) {
    this.city = city;
    return this;
  }

  /**
   * Get city
   * @return city
   **/
  @Schema(example = "Oegstgeest", required = true, description = "")
      @NotNull

    public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AddressDTO country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   **/
  @Schema(example = "The Netherlands", required = true, description = "")
      @NotNull

    public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDTO addressDTO = (AddressDTO) o;
    return Objects.equals(this.streetName, addressDTO.streetName) &&
        Objects.equals(this.houseNumber, addressDTO.houseNumber) &&
        Objects.equals(this.postalCode, addressDTO.postalCode) &&
        Objects.equals(this.city, addressDTO.city) &&
        Objects.equals(this.country, addressDTO.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(streetName, houseNumber, postalCode, city, country);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressDTO {\n");
    
    sb.append("    streetName: ").append(toIndentedString(streetName)).append("\n");
    sb.append("    houseNumber: ").append(toIndentedString(houseNumber)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
