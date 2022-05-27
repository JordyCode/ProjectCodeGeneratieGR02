package io.swagger.api.model.Entity;

import io.swagger.api.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    void newAccountShouldNotBeNull(){
        Assertions.assertNotNull(account);
    }

    @Test
    void AccountContainsData(){
        account.setIBAN("NL00INHO0000000002");
        account.setUser(user1);
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setBalance(1000.00);
        account.setAbsoluteLimit(-500.00);

        assertNotNull(account);
        assertEquals(account.getIBAN(), "NL00INHO0000000002");
        assertEquals(account.getUser().getUsername(), "test");
    }

}