package io.swagger.api.model.Entity;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")

@Entity
public class User {

  @Id
  @GeneratedValue
  @JsonProperty("userId")
  private Long userId;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("email")
  private String email;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("dateOfBirth")
  private String dateOfBirth;

//  @JsonProperty("address")
//  private String address;
//
//  @JsonProperty("phoneNumber")
//  private String phoneNumber;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<io.swagger.api.model.Role> roles;

  @JsonProperty("accounts")
  @JsonManagedReference
  @OneToMany(mappedBy = "user")
  private List<Account> accounts;

  /**
   * Gets or Sets userStatus
   */
  public enum UserStatusEnum {
    ACTIVE("Active"),
    
    INACTIVE("Inactive");

    private String value;

    UserStatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static UserStatusEnum fromValue(String text) {
      for (UserStatusEnum b : UserStatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("userStatus")
  private UserStatusEnum userStatus = null;

  @JsonProperty("dayLimit")
  private Double dayLimit = null;

  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;

  public User userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(example = "10", required = true, description = "")
      @NotNull

    public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public User firstName(String firstName) {
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

  public User lastName(String lastName) {
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

  public User email(String email) {
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

  public User dateOfBirth(String dateOfBirth) {
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
    public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public User password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "secret", required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public User accounts(List<Account> accounts) {
    this.accounts = accounts;
    return this;
  }

  /**
   * Get accounts
   * @return accounts
   **/
  @Schema(required = false, description = "")

    @Valid
    public List<Account> getAccounts() {
    return accounts;
  }

  public void setAccounts(List<Account> accounts) {
    this.accounts = accounts;
  }

  public User userStatus(UserStatusEnum userStatus) {
    this.userStatus = userStatus;
    return this;
  }

  /**
   * Get userStatus
   * @return userStatus
   **/
  @Schema(example = "Active", required = true, description = "")
      @NotNull

    public UserStatusEnum getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(UserStatusEnum userStatus) {
    this.userStatus = userStatus;
  }

  public List<io.swagger.api.model.Role> getRoles() {
    return roles;
  }

  public void setRoles(List<io.swagger.api.model.Role> roles) {
    this.roles = roles;
  }

  public User dayLimit(Double dayLimit) {
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
    if(dayLimit < 0)
      throw new IllegalArgumentException("Day limit can not be lower than 0");
    else
      this.dayLimit = dayLimit;
  }

  public User transactionLimit(Double transactionLimit) {
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
    if(transactionLimit < 0)
      throw new IllegalArgumentException("Transaction limit can not be lower than 0");
    else
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
    User user = (User) o;
    return Objects.equals(this.userId, user.userId) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.email, user.email) &&
        Objects.equals(this.dateOfBirth, user.dateOfBirth) &&
        Objects.equals(this.username, user.username) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.accounts, user.accounts) &&
        Objects.equals(this.userStatus, user.userStatus) &&
        Objects.equals(this.dayLimit, user.dayLimit) &&
        Objects.equals(this.transactionLimit, user.transactionLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, firstName, lastName, email, dateOfBirth, username, password, userStatus, dayLimit, transactionLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    accounts: ").append(toIndentedString(accounts)).append("\n");
    sb.append("    userStatus: ").append(toIndentedString(userStatus)).append("\n");
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

  public User(Long userId, String firstName, String lastName, String email, String dateOfBirth, String username, String password, UserStatusEnum userStatus, Double dayLimit, Double transactionLimit, List<io.swagger.api.model.Role> roles) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfBirth = dateOfBirth;
    this.username = username;
    this.password = password;
    this.accounts = accounts;
    this.userStatus = userStatus;
    this.dayLimit = dayLimit;
    this.transactionLimit = transactionLimit;
    this.roles = roles;
  }

  public User() {
  }

  public User(String name) {
    this.firstName = firstName;
  }

  public User(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

}
