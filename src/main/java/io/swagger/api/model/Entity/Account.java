package io.swagger.api.model.Entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Account
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")

@Entity
public class Account {

  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Long id = null;
  @JsonProperty("IBAN")
  private String IBAN = null;
  @ManyToOne(cascade = CascadeType.ALL)
  private User owner;

  /**
   * Gets or Sets accountType
   */
  public enum AccountTypeEnum {
    CURRENT("Current"),
    
    SAVINGS("Savings");

    private String value;

    AccountTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AccountTypeEnum fromValue(String text) {
      for (AccountTypeEnum b : AccountTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  public enum AccountStatusEnum {
    ACTIVE("active"),

    INACTIVE("inactive");

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

  @JsonProperty("accountType")
  private AccountTypeEnum accountType;

  @JsonProperty("balance")
  private Double balance;

  @JsonProperty("absoluteLimit")
  private Double absoluteLimit;

  @JsonProperty("accountStatus")
  private AccountStatusEnum accountStatus = null;

  public Account IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NL91 ABNA 0417 1643 00", required = true, description = "")
      @NotNull

    public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public Account accountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(example = "Current", required = true, description = "")
      @NotNull

    public AccountTypeEnum getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountTypeEnum accountType) {
    this.accountType = accountType;
  }

  public Account balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * minimum: -999
   * @return balance
   **/
  @Schema(example = "3242.11", required = true, description = "")
      @NotNull

  @DecimalMin("-999")  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public Account absoluteLimit(Double absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
    return this;
  }

  public Account accountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

  /**
   * Get accountStatus
   *
   * @return accountStatus
   **/
  @Schema(example = "active", description = "")

  public AccountStatusEnum getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(AccountStatusEnum accountStatus) {
    this.accountStatus = accountStatus;
  }

  /**
   * Get absoluteLimit
   * @return absoluteLimit
   **/
  @Schema(example = "-500", required = true, description = "")
      @NotNull

    public Double getAbsoluteLimit() {
    return absoluteLimit;
  }

  public void setAbsoluteLimit(Double absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return Objects.equals(this.IBAN, account.IBAN) &&
            Objects.equals(this.accountType, account.accountType) &&
            Objects.equals(this.balance, account.balance) &&
            Objects.equals(this.absoluteLimit, account.absoluteLimit) &&
            Objects.equals(this.accountStatus, account.accountStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(IBAN, accountType, balance, absoluteLimit, accountStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Account {\n");
    
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
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

  public User getUser() {
    return owner;
  }

  public void setUser(User user) {
    this.owner = user;
  }

  public Account(String IBAN, User user, AccountTypeEnum accountType, Double balance, Double absoluteLimit, AccountStatusEnum accountStatus) {
    this.IBAN = IBAN;
    this.owner = user;
    this.accountType = accountType;
    this.balance = balance;
    this.absoluteLimit = absoluteLimit;
    this.accountStatus = accountStatus;
  }

  public Account() {
  }
}
