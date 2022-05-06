package io.swagger.api.model.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.api.model.DTO.UserDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
    @Id
    private UUID userID;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    public enum role {
        CUSTOMER("Customer"),

        EMPLOYEE("Employee");

        private String value;

        role(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static User.role fromValue(String text) {
            for (User.role b : User.role.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
    @JsonProperty("accountList")
    @JsonManagedReference
    @OneToMany(mappedBy = "userID")
    private List<Account> accountList;

    private Double dayLimit = null;

    private Double transactionLimit = null;

    public User() {
    }

    public User(UUID userID, String firstName, String lastName, String email, String address, String phoneNumber, List<Account> accountList, Double dayLimit, Double transactionLimit) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountList = accountList;
        this.dayLimit = dayLimit;
        this.transactionLimit = transactionLimit;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public Double getDayLimit() {
        return dayLimit;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }
}
