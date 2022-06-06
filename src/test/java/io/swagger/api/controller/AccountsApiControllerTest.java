package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @MockBean
    private TransactionService transactionService;

    User user1 = new User();
    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();
    List<Account> accounts =  new ArrayList<>();
    List<Account> usersAccountList =  new ArrayList<>();


    @BeforeEach
    public void setup() {
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));


        account1.setId(2L);
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setIBAN("NL00INHO000000002");
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        account2.setId(3L);
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setIBAN("NL00INHO000000003");
        account2.setBalance(320.00);
        account2.setAbsoluteLimit(50.00);
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        account3.setId(4L);
        account3.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account3.setIBAN("NL00INHO000000004");
        account3.setBalance(1500.00);
        account3.setAbsoluteLimit(20.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        accounts.add(account1);
        accounts.add(account2);
        usersAccountList.add(account1);
        usersAccountList.add(account3);
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getAccountsAsEmployeeShouldReturnAllAccountsAndOk() throws Exception {
        given(accountService.getAllAccounts()).willReturn(accounts);
        this.mockMvc.perform(get("/accounts").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "FreddyUser", password = "welkom10", roles = "USER")
    public void getAccountsAsUserShouldReturnOnlyUsersAccountAndOk() throws Exception {
        this.mockMvc.perform(get("/accounts/10").contentType("application/json")).andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void getSpecificTransactionWhenUserShouldReturnFound() throws Exception {
        this.mockMvc.perform(get("/transactions/12"))
                .andExpect(status().isFound());
    }

    /// Werkt nog niet
    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getSpecificAccountAsEmployeeShouldReturnOk() throws Exception {
        given(accountService.getAccountById(2L)).willReturn(account1);
        this.mockMvc.perform(get("/accounts/2").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "USER")
    public void getSpecificAccountAsUserShouldReturnForbidden() throws Exception {
        given(accountService.getAccountById(2L)).willReturn(account1);
        this.mockMvc.perform(get("/accounts/2").contentType("application/json")).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getSpecificAccountWithIDIs1ShouldReturnForbidden() throws Exception {
        given(accountService.getAccountById(2L)).willReturn(account1);
        this.mockMvc.perform(get("/accounts/1").contentType("application/json")).andExpect(status().isForbidden());
    }



    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void createAccountAsEmployeeShouldReturnOk() throws Exception
    {
        given(accountService.add(account1, false)).willReturn(account1);
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/accounts")
                        .content(asJsonString(account1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "USER")
    public void createAccountAsUserShouldReturnBadRequest() throws Exception
    {
        given(accountService.add(account1, false)).willReturn(account1);
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/accounts")
                        .content(asJsonString(account1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeAccountDetailsAsEmployeeShouldReturnOK() throws Exception{
        given(accountService.save(account1)).willReturn(account1);
        this.mockMvc.perform(put("/accounts/2").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeAccountDetailsAsUserShouldReturnForbidden() throws Exception{
        given(accountService.save(account1)).willReturn(account1);
        this.mockMvc.perform(put("/accounts/2").contentType("application/json")).andExpect(status().isForbidden());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}