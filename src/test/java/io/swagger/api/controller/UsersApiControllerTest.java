package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.service.UserService;
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

import static io.swagger.api.controller.AccountsApiControllerTest.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    User user1 = new User();
    User user2 = new User();
    Account account1 = new Account();
    Account account2 = new Account();
    Account account3 = new Account();

    List<User> users = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    List<Account> usersAccountList = new ArrayList<>();


    @BeforeEach
    public void setup() {

        user1.setUserId(2L);
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

        users.add(user1);
        users.add(user2);
        userList.add(user1);
        accounts.add(account1);
        accounts.add(account2);
        usersAccountList.add(account1);
        usersAccountList.add(account3);
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getUsersAsEmployeeShouldReturnAllUsersAndOk() throws Exception {
        given(userService.getAllUsers()).willReturn(users);
        this.mockMvc.perform(get("/users").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "USER")
    public void getUsersAsUserShouldReturnOnlyTheUserAndOk() throws Exception {
        given(userService.getAllUsers()).willReturn(userList);
        this.mockMvc.perform(get("/users").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void getSpecificUserAsEmployeeShouldFullDetailsAndReturnOk() throws Exception {
        given(userService.getSpecificUser(2L)).willReturn(user1);
        this.mockMvc.perform(get("/users/2").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "USER")
    public void getSpecificUserAsUserShouldFewDetailsAndReturnOk() throws Exception {
        given(userService.getSpecificUser(2L)).willReturn(user1);
        this.mockMvc.perform(get("/users/2").contentType("application/json")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    public void createUserAsEmployeeShouldReturnOk() throws Exception {
        given(userService.add(user1, false)).willReturn(user1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "USER")
    public void createUserAsUserShouldReturnBadRequest() throws Exception {
        given(userService.add(user1, false)).willReturn(user1);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeAccountDetailsAsEmployeeShouldReturnOK() throws Exception{
        given(userService.saveUser(user1)).willReturn(user1);
        this.mockMvc.perform(put("/users/2").contentType("application/json")).andExpect(status().isOk());
    }


    // Toestaan dat User bepaalde properties mag changen? Zoals Email
    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeAccountDetailsAsUserShouldReturnForbidden() throws Exception{
        given(userService.saveUser(user1)).willReturn(user1);
        this.mockMvc.perform(put("/users/2").contentType("application/json")).andExpect(status().isForbidden());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
