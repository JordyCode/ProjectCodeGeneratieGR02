package io.swagger.api.model.DTO;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * DepositTransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-29T10:19:29.603Z[GMT]")


public class DepositTransactionDTO   {
    @JsonProperty("accountTo")
    private String accountTo = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("performedBy")
    private Integer performedBy = null;

    public DepositTransactionDTO accountTo(String accountTo) {
        this.accountTo = accountTo;
        return this;
    }

    /**
     * Get accountTo
     * @return accountTo
     **/
    @Schema(description = "")

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public DepositTransactionDTO amount(Double amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Get amount
     * @return amount
     **/
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public DepositTransactionDTO performedBy(Integer performedBy) {
        this.performedBy = performedBy;
        return this;
    }

    /**
     * Get performedBy
     * @return performedBy
     **/
    @Schema(example = "151", required = true, description = "")

    public Integer getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Integer performedBy) {
        this.performedBy = performedBy;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepositTransactionDTO depositTransactionDTO = (DepositTransactionDTO) o;
        return Objects.equals(this.accountTo, depositTransactionDTO.accountTo) &&
                Objects.equals(this.amount, depositTransactionDTO.amount) &&
                Objects.equals(this.performedBy, depositTransactionDTO.performedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountTo, amount, performedBy);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DepositTransactionDTO {\n");

        sb.append("    accountTo: ").append(toIndentedString(accountTo)).append("\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    performedBy: ").append(toIndentedString(performedBy)).append("\n");
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