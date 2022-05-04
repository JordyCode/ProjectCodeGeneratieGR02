package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.AccountDTO;
import io.swagger.model.AddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.LocalDate;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UserDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:28:37.037Z[GMT]")


public class UserDTO   {
  @JsonProperty("userId")
  private Integer userId = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("dateOfBirth")
  private LocalDate dateOfBirth = null;

  @JsonProperty("address")
  private AddressDTO address = null;

  @JsonProperty("phoneNumber")
  private String phoneNumber = null;

  /**
   * Gets or Sets role
   */
  public enum RoleEnum {
    CUSTOMER("Customer"),
    
    EMPLOYEE("Employee");

    private String value;

    RoleEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static RoleEnum fromValue(String text) {
      for (RoleEnum b : RoleEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("role")
  private RoleEnum role = null;

  @JsonProperty("accounts")
  private AccountDTO accounts = null;

  /**
   * Gets or Sets accountStatus
   */
  public enum AccountStatusEnum {
    ACTIVE("Active"),
    
    INACTIVE("Inactive");

    private String value;

    AccountStatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccountStatusEnum fromValue(String text) {
      for (AccountStatusEnum b : AccountStatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("accountStatus")
  private AccountStatusEnum accountStatus = null;

  @JsonProperty("dayLimit")
  private Double dayLimit = null;

  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;

  public UserDTO userId(Integer userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "10", required = true, description = "")
      @NotNull

    public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public UserDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "Peter", required = true, description = "")
      @NotNull

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UserDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "Visser", required = true, description = "")
      @NotNull

    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UserDTO email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   **/
  @Schema(example = "Peter_Visser@hotmail.com", required = true, description = "")
      @NotNull

    public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public UserDTO dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
   * @return dateOfBirth
   **/
  @Schema(example = "Tue May 23 00:00:00 GMT 13", required = true, description = "")
      @NotNull

    @Valid
    public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public UserDTO address(AddressDTO address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
  }

  public UserDTO phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   **/
  @Schema(example = "683726482", required = true, description = "")
      @NotNull

    public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public UserDTO role(RoleEnum role) {
    this.role = role;
    return this;
  }

  /**
   * Get role
   * @return role
   **/
  @Schema(example = "Customer", required = true, description = "")
      @NotNull

    public RoleEnum getRole() {
    return role;
  }

  public void setRole(RoleEnum role) {
    this.role = role;
  }

  public UserDTO accounts(AccountDTO accounts) {
    this.accounts = accounts;
    return this;
  }

  /**
   * Get accounts
   * @return accounts
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public AccountDTO getAccounts() {
    return accounts;
  }

  public void setAccounts(AccountDTO accounts) {
    this.accounts = accounts;
  }

  public UserDTO accountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

  /**
   * Get accountStatus
   * @return accountStatus
   **/
  @Schema(example = "Active", required = true, description = "")
      @NotNull

    public AccountStatusEnum getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
  }

  public UserDTO dayLimit(Double dayLimit) {
    this.dayLimit = dayLimit;
    return this;
  }

  /**
   * Get dayLimit
   * @return dayLimit
   **/
  @Schema(example = "200", required = true, description = "")
      @NotNull

    public Double getDayLimit() {
    return dayLimit;
  }

  public void setDayLimit(Double dayLimit) {
    this.dayLimit = dayLimit;
  }

  public UserDTO transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Get transactionLimit
   * @return transactionLimit
   **/
  @Schema(example = "2000", required = true, description = "")
      @NotNull

    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserDTO userDTO = (UserDTO) o;
    return Objects.equals(this.userId, userDTO.userId) &&
        Objects.equals(this.firstName, userDTO.firstName) &&
        Objects.equals(this.lastName, userDTO.lastName) &&
        Objects.equals(this.email, userDTO.email) &&
        Objects.equals(this.dateOfBirth, userDTO.dateOfBirth) &&
        Objects.equals(this.address, userDTO.address) &&
        Objects.equals(this.phoneNumber, userDTO.phoneNumber) &&
        Objects.equals(this.role, userDTO.role) &&
        Objects.equals(this.accounts, userDTO.accounts) &&
        Objects.equals(this.accountStatus, userDTO.accountStatus) &&
        Objects.equals(this.dayLimit, userDTO.dayLimit) &&
        Objects.equals(this.transactionLimit, userDTO.transactionLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, lastName, email, dateOfBirth, address, phoneNumber, role, accounts, accountStatus, dayLimit, transactionLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserDTO {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    role: ").append(toIndentedString(role)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
    sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
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
