package io.swagger.api.model.Entity;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")

@Entity
public class Transaction {
  @GeneratedValue
  @Id
  @JsonProperty("transactionId")
  private Integer transactionId = null;

  @JsonProperty("user")
  @ManyToOne
  @JsonBackReference
  private User user;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    TRANSACTION("Transaction"),

    DEPOSIT("Deposit"),

    WITHDRAW("Withdraw");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String text) {
      for (TypeEnum b : TypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("type")
  private TypeEnum type = null;

  @JsonProperty("timestamp")
  private String timestamp = null;

  @JsonProperty("accountfrom")
  private String accountFrom = null;

  @JsonProperty("accountto")
  private String accountTo = null;

  @JsonProperty("performedBy")
  private Integer performedBy = null;

  @JsonProperty("amount")
  private Double amount = null;

  public Transaction transactionId(Integer transactionId) {
    this.transactionId = transactionId;
    return this;
  }
  public Transaction(Integer transactionId, Transaction.TypeEnum type, User user, String timestamp, String accountFrom, String accountTo, Integer performedBy, Double amount) {
    this.transactionId = transactionId;
    this.type = type;
    this.user = user;
    this.timestamp = timestamp;
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.performedBy = performedBy;
    this.amount = amount;
  }

  public Transaction(String accountFrom, String accountTo, Double amount) {
    this.accountFrom = accountFrom;
    this.accountTo = accountTo;
    this.amount = amount;
  }

  public Transaction() {

  }

  /**
   * Get transactionId
   * @return transactionId
   **/
  @Schema(example = "1", required = true, description = "")
  @NotNull

  public Integer getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(Integer transactionId) {
    this.transactionId = transactionId;
  }

  public Transaction type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   **/
  @Schema(example = "Deposit", required = true, description = "")
  @NotNull

  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  public Transaction timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Get timestamp
   * @return timestamp
   **/
  @Schema(example = "29/04/2021", required = true, description = "")
  @NotNull

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Transaction accountFrom(String accountFrom) {
    this.accountFrom = accountFrom;
    return this;
  }

  /**
   * Get accountFrom
   * @return accountFrom
   **/
  @Schema(example = "NL20INHO0032076001", required = true, description = "")
  @NotNull

  public String getAccountFrom() {
    return accountFrom;
  }

  public void setAccountFrom(String accountFrom) {
    this.accountFrom = accountFrom;
  }

  public Transaction accountTo(String accountTo) {
    this.accountTo = accountTo;
    return this;
  }

  /**
   * Get accountTo
   * @return accountTo
   **/
  @Schema(example = "NL20INHO0032070023", required = true, description = "")
  @NotNull

  public String getAccountTo() {
    return accountTo;
  }

  public void setAccountTo(String accountTo) {
    this.accountTo = accountTo;
  }

  public Transaction performedBy(Integer performedBy) {
    this.performedBy = performedBy;
    return this;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


  /**
   * Get performedBy
   * @return performedBy
   **/
  @Schema(example = "151", required = true, description = "")
  @NotNull

  public Integer getPerformedBy() {
    return performedBy;
  }

  public void setPerformedBy(Integer performedBy) {
    this.performedBy = performedBy;
  }

  public Transaction amount( Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "200.00", required = true, description = "")
  @NotNull

  @Valid
  public  Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.transactionId, transaction.transactionId) &&
            Objects.equals(this.type, transaction.type) &&
            Objects.equals(this.timestamp, transaction.timestamp) &&
            Objects.equals(this.accountFrom, transaction.accountFrom) &&
            Objects.equals(this.accountTo, transaction.accountTo) &&
            Objects.equals(this.performedBy, transaction.performedBy) &&
            Objects.equals(this.amount, transaction.amount) &&
            Objects.equals(this.user, transaction.user);

  }

  @Override
  public int hashCode() {
    return Objects.hash(transactionId, type, timestamp, accountFrom, accountTo, performedBy, amount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");

    sb.append("    transactionId: ").append(toIndentedString(transactionId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
    sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
    sb.append("    performedBy: ").append(toIndentedString(performedBy)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
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