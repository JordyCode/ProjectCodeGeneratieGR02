package io.swagger.api.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    @JsonProperty("id")
    private Long id;

    @NotNull
    private Account.AccountTypeEnum accountTypeEnum;

    @NotNull
    private Account.AccountStatusEnum accountStatusEnum;

    @NotNull
    private User user;

    private String IBAN;

    private double balance;

    private double absoluteLimit;

    public Account.AccountStatusEnum getAccountStatusEnum() {
        return accountStatusEnum;
    }

    public void setAccountStatusEnum(Account.AccountStatusEnum accountStatusEnum) {
        this.accountStatusEnum = accountStatusEnum;
    }

    public Account.AccountTypeEnum getAccountTypeEnum() {
        return accountTypeEnum;
    }

    public void setAccountTypeEnum(Account.AccountTypeEnum accountTypeEnum) {
        this.accountTypeEnum = accountTypeEnum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAbsoluteLimit() {
        return absoluteLimit;
    }

    public void setAbsoluteLimit(double absoluteLimit) {
        this.absoluteLimit = absoluteLimit;
    }
}
