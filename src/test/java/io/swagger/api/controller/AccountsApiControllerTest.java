package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.service.AccountService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private TransactionService transactionService;

    private final ObjectMapper mapper = new ObjectMapper();

    User user1 = new User();
    User user2 = new User();
    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();
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
        testUser1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");

        testUser2.setUserId(4L);
        testUser2.setUsername("UserBank123");
        testUser2.setPassword("user123");
        testUser2.setFirstName("Frank");
        testUser2.setLastName("Dersjant");
        testUser2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser2.setDayLimit(1000.00);
        testUser2.setTransactionLimit(500.00);
        testUser2.setEmail("frank.dersjant@test.com");
        testUser2.setDateOfBirth("01/01/1970");


        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setUser(testUser1);
        account1.setIBAN("NL00INHO000000007");
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);


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
        this.mockMvc.perform(get("/accounts").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void getAccountsAsUserShouldReturnOnlyUsersAccountAndOk() throws Exception {
        this.mockMvc.perform(get("/accounts").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void getSpecificAccountAsEmployeeShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/accounts/6").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void getSpecificAccountAsUserShouldReturnForbidden() throws Exception {
        this.mockMvc.perform(get("/accounts/6").contentType("application/json")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void createAccountAsEmployeeShouldReturnOK() throws Exception {

        Account account = new Account();
        account.setId(20L);
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setUser(testUser1);
        account.setIBAN("NL00INHO000000007");
        account.setBalance(150.00);
        account.setAbsoluteLimit(100.00);
        account.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        mockMvc.perform(post("/accounts")
                        .content(mapper.writeValueAsString(account))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void createAccountAsUserShouldReturnBadRequest() throws Exception
    {
        this.mockMvc.perform( post("/accounts")
                        .content(asJsonString(account1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void updateAccountAsEmployeeShouldReturnOK() throws Exception {
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");

        this.mockMvc.perform(put("/users/3").content(asJsonString(testUser1)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void updateAccountAsUserShouldReturnForbidden() throws Exception {
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");

        this.mockMvc.perform(put("/users/3").content(asJsonString(testUser1)).contentType(MediaType.APPLICATION_JSON))
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