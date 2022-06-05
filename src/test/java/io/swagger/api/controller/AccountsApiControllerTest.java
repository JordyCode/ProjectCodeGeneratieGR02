package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @BeforeEach
    public void setup() {

        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        Account account1 = new Account();
        account1.setId(3L);
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setIBAN("NL00INHO000000001");
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getAccountShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/accounts")).andExpect(status().isOk());
    }

    /// Werkt nog niet
    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getSpecificAccountShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/accounts/3")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john",password = "test", roles = "EMPLOYEE")
    public void createAccountShouldReturnOk() throws Exception
    {
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        Account account = new Account();
        account.setIBAN("NL00INHO000000001");
        account.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account.setId(4L);
        account.setAbsoluteLimit(100.00);
        account.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account.setBalance(1500.00);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/accounts")
                        .content(asJsonString(account))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        //////// Put, TotalBalance methods nog maken
    }
}