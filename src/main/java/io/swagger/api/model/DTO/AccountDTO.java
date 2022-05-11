package io.swagger.api.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class AccountDTO {
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
        public static AccountDTO.AccountTypeEnum fromValue(String text) {
            for (AccountDTO.AccountTypeEnum b : AccountDTO.AccountTypeEnum.values()) {
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
