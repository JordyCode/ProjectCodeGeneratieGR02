package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private final ObjectMapper mapper = new ObjectMapper();

    User user1 = new User();
    User user2 = new User();
    AccountDTO account1 = new AccountDTO();
    Account account2 = new Account();
    User testUser1 = new User();
    User testUser2 = new User();

    List<User> users = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<User> usersNoAccountList = new ArrayList<>();


    @BeforeEach
    public void setup() {
        testUser1.setUserId(2L);
        testUser1.setUsername("EmployeeBank123");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");

        testUser2.setUserId(4L);
        testUser2.setUsername("UserBank123");
        testUser2.setPassword("user123");
        testUser2.setFirstName("Frank");
        testUser2.setLastName("Dersjant");
        testUser2.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser2.setDayLimit(1000.00);
        testUser2.setTransactionLimit(500.00);
        testUser2.setEmail("frank.dersjant@test.com");
        testUser2.setDateOfBirth("01/01/1970");

        account1.setAccountTypeEnum(Account.AccountTypeEnum.CURRENT);
        account1.setUser(testUser1);
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatusEnum(Account.AccountStatusEnum.ACTIVE);

        account2.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account2.setUser(testUser2);
        account2.setIBAN("NL00INHO000000003");
        account2.setBalance(320.00);
        account2.setAbsoluteLimit(50.00);
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        users.add(user1);
        users.add(user2);
        userList.add(user1);
        usersNoAccountList.add(user2);
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void getAccountsAsEmployeeShouldReturnAllAccountsAndOk() throws Exception {
        mockMvc.perform(get("/accounts").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].balance").value(150000000))
                .andExpect(jsonPath("$[5].id").value(11))
                .andExpect(jsonPath("$[5].absoluteLimit").value(-100.00))
                .andExpect(jsonPath("$[3].accountType").value("Current"))
                .andExpect(jsonPath("$[2].balance").value(0.00))
                .andExpect(jsonPath("$[1].accountStatus").value("Active"));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void getAccountsAsUserShouldReturnOnlyUsersAccountAndOk() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(6))
                .andExpect(jsonPath("$[0].balance").value(0.00))
                .andExpect(jsonPath("$[0].accountType").value("Current"))
                .andExpect(jsonPath("$[0].accountStatus").value("Active"))
                .andExpect(jsonPath("$[1].accountStatus").value("Inactive"))
                .andExpect(jsonPath("$[1].id").value(7))
                .andExpect(jsonPath("$[1].accountType").value("Savings"));
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void getSpecificAccountAsEmployeeShouldReturnOk() throws Exception {
        mockMvc.perform(get("/accounts/9").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500.00))
                .andExpect(jsonPath("$.accountStatus").value("Active"))
                .andExpect(jsonPath("$.id").value(9));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void getSpecificAccountAsUserShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/accounts/2").contentType("application/json"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void getSpecificAccountAsEmployeeInvalidIDShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/accounts/20").contentType("application/json"))
                .andExpect(jsonPath("$").value(containsString("Id not found")))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void createAccountAsEmployeeShouldReturnIsCreated() throws Exception {
        mockMvc.perform(post("/accounts")
                        .content(mapper.writeValueAsString(account1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance").value(150.00))
                .andExpect(jsonPath("$.accountStatus").value("Active"))
                .andExpect(jsonPath("$.absoluteLimit").value(100.00))
                .andExpect(jsonPath("$.id").value(12));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void createAccountAsUserShouldReturnForbidden() throws Exception {
        mockMvc.perform(post("/accounts")
                        .content(asJsonString(account1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void updateAccountAsEmployeeShouldReturnOK() throws Exception {
        account2.setId(6L);
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setUser(testUser2);
        account2.setBalance(500.00);
        account2.setAccountStatus(Account.AccountStatusEnum.INACTIVE);
        account2.setAbsoluteLimit(-100.00);

        mockMvc.perform(put("/accounts/6").content(asJsonString(account2)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500.00))
                .andExpect(jsonPath("$.accountStatus").value("Inactive"))
                .andExpect(jsonPath("$.absoluteLimit").value(-100.00))
                .andExpect(jsonPath("$.id").value(6));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void updateAccountAsUserShouldReturnForbidden() throws Exception {
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");

        mockMvc.perform(put("/users/3").content(asJsonString(testUser1)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}