package io.swagger.api.model.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;


public class UserDTO {

    private Long id;

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
        public static UserDTO.role fromValue(String text) {
            for (UserDTO.role b : UserDTO.role.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }
    }
    @JsonProperty("accountDTOList")
    @JsonManagedReference
//    @OneToMany(mappedBy = "id")
    private List<AccountDTO> accountDTOList;

    private Double dayLimit = null;

    private Double transactionLimit = null;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String address, String phoneNumber, List<AccountDTO> accountDTOList, Double dayLimit, Double transactionLimit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.accountDTOList = accountDTOList;
        this.dayLimit = dayLimit;
        this.transactionLimit = transactionLimit;
    }

    public Long getid() {
        return id;
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

    public List<AccountDTO> getAccountList() {
        return accountDTOList;
    }

    public Double getDayLimit() {
        return dayLimit;
    }

    public Double getTransactionLimit() {
        return transactionLimit;
    }
}
