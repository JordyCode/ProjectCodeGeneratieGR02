package io.swagger.api.model.Entity;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest (classes = { Swagger2SpringBoot.class })
@AutoConfigureMockMvc
class AccountTest {

    @Autowired
    private MockMvc mockMvc;

    private Account account;
    private User user1;

    @BeforeEach
    public void setup() {
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        account = new Account();
    }
    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void newAccountShouldNotBeNull() {
        Assertions.assertNotNull(account);
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void accountContainsData() {
        account.setIBAN("NL00INHO0000000002");
        account.setUser(user1);
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setBalance(1000.00);
        account.setAbsoluteLimit(-500.00);

        assertNotNull(account);
        assertEquals("NL00INHO0000000002", account.getIBAN());
        assertEquals(user1, account.getUser());
        assertEquals(Account.AccountTypeEnum.CURRENT, account.getAccountType());
        assertEquals((Double)1000.00, account.getBalance());
        assertEquals((Double)(-500.00), account.getAbsoluteLimit());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setBalanceToPositiveNumberShouldSetThatBalance() {
        Double newBalance = 1000.00;
        account.setBalance(newBalance);
        assertEquals(newBalance, account.getBalance());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setBalanceToNegativeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> account.setBalance(-1.00));
    }
    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setAccountStatusToActive() {
        account.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        assertEquals(Account.AccountStatusEnum.ACTIVE, account.getAccountStatus());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setAccountStatusToInactive() {
        account.setAccountStatus(Account.AccountStatusEnum.INACTIVE);
        assertEquals(Account.AccountStatusEnum.INACTIVE, account.getAccountStatus());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setAbsoluteLimitAboveOneHundredShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> account.setAbsoluteLimit(120.00));
    }
}