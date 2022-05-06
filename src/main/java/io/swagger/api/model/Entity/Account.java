package io.swagger.api.model.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.api.model.DTO.AccountDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Account {
    @Id
    private String IBAN;

    private enum AccountTypeEnum {
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
        public static Account.AccountTypeEnum fromValue(String text) {
            for (Account.AccountTypeEnum b : Account.AccountTypeEnum.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }

    private double balance;

    private UUID userID;
}
