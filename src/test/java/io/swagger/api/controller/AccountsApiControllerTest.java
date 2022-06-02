package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import org.junit.Before;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Arrays;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountsApiController.class)
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

//    @Test
//    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
//    public void getAccountShouldReturnOk() throws Exception {
//        this.mockMvc.perform(get("/accounts")).andExpect(status().isOk());
//    }


//    @Before
//    public void setup() {
//
//        User user1 = new User();
//        user1.setUsername("testuser1");
//        user1.setPassword("welkom10");
//        user1.setEmail("testuser1@mail.com");
//        user1.setTransactionLimit(1000.00);
//        user1.setDayLimit(10000.00);
//        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
//        user1.setRoles(Arrays.asList(Role.ROLE_USER));
//
//        Account account =  new Account();
//
//        account = new Account();
//        account.setIBAN("NL00INHO0000000002");
//        account.setUser(user1);
//        account.setAccountType(Account.AccountTypeEnum.CURRENT);
//        account.setBalance(1000.00);
//        account.setAbsoluteLimit(-500.00);
//    }


    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    void getAllAccountsShouldReturnJsonArrayOfSizeOne() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(List.of(new Account("NL00INHO000000002", Account.AccountTypeEnum.CURRENT, 200.00, 100.00, Account.AccountStatusEnum.ACTIVE)));
        this.mockMvc.perform(get("/accounts"))
                .andDo(print()).andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
//    void createAccountShouldReturnStatusCreatedAndOneObject() throws Exception {
//        Account account = new Account();
//        account.setIBAN("NL00INHO000000001");
//        account.setAccountType(Account.AccountTypeEnum.SAVINGS);
//        account.setId(4L);
//        account.setAbsoluteLimit(100.00);
//        account.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
//        account.setBalance(1500.00);
//        //AccountDTO dto = new AccountDTO();
//        when(accountService.addTest(any(Account.class))).thenReturn(account);
//        mockMvc.perform(post("/accounts")
//                        .content(mapper.writeValueAsString(account))
//                        .characterEncoding("utf-8")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.user.name").value("Frank"));
//
//    }
}
