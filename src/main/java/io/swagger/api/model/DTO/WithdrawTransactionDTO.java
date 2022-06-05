package io.swagger.api.model.DTO;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * WithdrawTransactionDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-29T10:19:29.603Z[GMT]")


public class WithdrawTransactionDTO   {
    @JsonProperty("accountFrom")
    private String accountFrom = null;

    @JsonProperty("amount")
    private Double amount = null;

    @JsonProperty("performedBy")
    private Integer performedBy = null;

    public WithdrawTransactionDTO accountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
        return this;
    }

    /**
     * Get accountFrom
     * @return accountFrom
     **/
    @Schema(description = "")

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public WithdrawTransactionDTO amount(Double amount) {
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

    public WithdrawTransactionDTO performedBy(Integer performedBy) {
        this.performedBy = performedBy;
        return this;
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


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WithdrawTransactionDTO withdrawTransactionDTO = (WithdrawTransactionDTO) o;
        return Objects.equals(this.accountFrom, withdrawTransactionDTO.accountFrom) &&
                Objects.equals(this.amount, withdrawTransactionDTO.amount) &&
                Objects.equals(this.performedBy, withdrawTransactionDTO.performedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountFrom, amount, performedBy);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WithdrawTransactionDTO {\n");

        sb.append("    accountFrom: ").append(toIndentedString(accountFrom)).append("\n");
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