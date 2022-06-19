package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private TransactionService transactionService;


    User user1 = new User();
    User user2 = new User();
    Account account1 = new Account();
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
        testUser1.setDateOfBirth("03/03/1970");

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

        account1.setId(2L);
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setUser(testUser1);
        account1.setIBAN("NL00INHO000000002");
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        account2.setId(3L);
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
    public void getUsersAsEmployeeShouldReturnAllUsersAndOk() throws Exception {
        mockMvc.perform(get("/users").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[1].userId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Willem"))
                .andExpect(jsonPath("$[1].lastName").value("Wiltenburg"))
                .andExpect(jsonPath("$[3].username").value("UserBank"))
                .andExpect(jsonPath("$[4].userStatus").value("Active"))
                .andExpect(jsonPath("$[3].dayLimit").value(700.00))
                .andExpect(jsonPath("$[4].email").value("test@mail.ml"))
                .andExpect(jsonPath("$[2].dateOfBirth").value("01/01/2001"));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void getUsersAsUserShouldReturnOnlyTheUserAndOk() throws Exception {
        mockMvc.perform(get("/users").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(4))
                .andExpect(jsonPath("$[0].username").value("UserBank"))
                .andExpect(jsonPath("$[0].transactionLimit").value(500.00))
                .andExpect(jsonPath("$[0].dayLimit").value(700.00))
                .andExpect(jsonPath("$[0].firstName").value("Frank"))
                .andExpect(jsonPath("$[0].email").value("frank.dersjant@test.com"))
                .andExpect(jsonPath("$[0].userStatus").value("Active"));
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void getSpecificUserAsEmployeeShouldFullDetailsAndReturnOk() throws Exception {
        mockMvc.perform(get("/users/3").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.email").value("test@mail.ml"))
                .andExpect(jsonPath("$.dateOfBirth").value("01/01/2001"))
                .andExpect(jsonPath("$.transactionLimit").value(500.00))
                .andExpect(jsonPath("$.username").value("EmptyUser"))
                .andExpect(jsonPath("$.dateOfBirth").value("01/01/2001"));
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void createUserAsEmployeeShouldReturnIsCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(testUser1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(6))
                .andExpect(jsonPath("$.lastName").value("Wiltenburg"))
                .andExpect(jsonPath("$.dayLimit").value(100.00))
                .andExpect(jsonPath("$.email").value("willem.wiltenburg@test.com"))
                .andExpect(jsonPath("$.transactionLimit").value(1000.00))
                .andExpect(jsonPath("$.username").value("EmployeeBank123"));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void createUserAsUserShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(asJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    public void updateAccountDetailsAsEmployeeShouldReturnOK() throws Exception {
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Visser");
        testUser1.setUserStatus(User.UserStatusEnum.INACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/1970");

        mockMvc.perform(put("/users/3").content(asJsonString(testUser1)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(3))
                .andExpect(jsonPath("$.username").value("EmployeeBank"))
                .andExpect(jsonPath("$.userStatus").value("Inactive"))
                .andExpect(jsonPath("$.lastName").value("Visser"))
                .andExpect(jsonPath("$.dayLimit").value(100.00))
                .andExpect(jsonPath("$.dayOfBirth").value("03/03/1970"))
                .andExpect(jsonPath("$.email").value("willem.wiltenburg@test.com"));
    }

    @Test
    @WithMockUser(username = "UserBank", password = "user123", roles = "USER")
    public void updateAccountDetailsAsUserShouldReturnForbidden() throws Exception {
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/1970");

        mockMvc.perform(put("/users/3").content(asJsonString(testUser1)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "EmployeeBank", password = "employee123", roles = "EMPLOYEE")
    void getAllUsersWithAccountsIsNullShouldReturnOK() throws Exception{
        mockMvc.perform(get("/usersWhenNull").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[3].userId").value(4))
                .andExpect(jsonPath("$[0].email").value("empty@empty.nl"))
                .andExpect(jsonPath("$[1].lastName").value("Wiltenburg"))
                .andExpect(jsonPath("$[2].transactionLimit").value(500.00))
                .andExpect(jsonPath("$[3].transactionLimit").value(500.00))
                .andExpect(jsonPath("$[4].dateOfBirth").value("01/01/2001"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
