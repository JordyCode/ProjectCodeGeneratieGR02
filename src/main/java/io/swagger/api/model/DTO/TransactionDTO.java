package io.swagger.api.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.Id;

public class TransactionDTO {

    private Integer transactionId;

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
        public static TransactionDTO.TypeEnum fromValue(String text) {
            for (TransactionDTO.TypeEnum b : TransactionDTO.TypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    private TypeEnum type;

    private String timestamp;

    private String accountFrom;

    private String accountTo;

    private Integer performedBy;

    private Double amount;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public Integer getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(Integer performedBy) {
        this.performedBy = performedBy;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
//
//    public TransactionDTO() {
//    }
//
//    public TransactionDTO(Integer transactionId, TypeEnum type, String timestamp, String accountFrom, String accountTo, Integer performedBy, Double amount) {
//        this.transactionId = transactionId;
//        this.type = type;
//        this.timestamp = timestamp;
//        this.accountFrom = accountFrom;
//        this.accountTo = accountTo;
//        this.performedBy = performedBy;
//        this.amount = amount;
//    }
}
