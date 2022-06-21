package io.swagger.api.model.Entity;

import io.swagger.Swagger2SpringBoot;
import io.swagger.api.model.Role;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = { Swagger2SpringBoot.class })
@AutoConfigureMockMvc
class TransactionTest {

    @Autowired
    private MockMvc mockMvc;

    private User user1;
    private User user2;
    private User employee1;
    private Account currentAccountUser1;
    private Account savingAccountUser1;
    private Account currentAccountUser2;
    private Account AccountEmployee;

    private Transaction transaction1;

    private Transaction transaction2;


    @BeforeEach
    public void prepareRefData(){

        // User
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setUserStatus(User.UserStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        user2 = new User();
        user2.setUsername("testuser2");
        user2.setPassword("welkom10");
        user2.setEmail("testuser2@mail.com");
        user2.setTransactionLimit(1000.00);
        user2.setDayLimit(10000.00);
        user2.setUserStatus(User.UserStatusEnum.ACTIVE);
        user2.setRoles(Arrays.asList(Role.ROLE_USER));

        employee1 = new User();
        employee1.setUsername("testuser3");
        employee1.setPassword("welkom10");
        employee1.setEmail("testuser3@mail.com");
        employee1.setTransactionLimit(10000.00);
        employee1.setDayLimit(100000.00);
        employee1.setUserStatus(User.UserStatusEnum.ACTIVE);
        employee1.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));

        // Account current for user 1
        currentAccountUser1 = new Account();
        currentAccountUser1.setId(40L);
        currentAccountUser1.setIBAN("NL00INHO0000000002");
        currentAccountUser1.setUser(user1);
        currentAccountUser1.setAccountType(Account.AccountTypeEnum.CURRENT);
        currentAccountUser1.setBalance(1000.00);
        currentAccountUser1.setAbsoluteLimit(-500.00);

        // Account savings for user 1
        savingAccountUser1 = new Account();
        savingAccountUser1.setId(41L);
        savingAccountUser1.setIBAN("NL00INHO0000004001");
        savingAccountUser1.setUser(user1);
        savingAccountUser1.setAccountType(Account.AccountTypeEnum.SAVINGS);
        savingAccountUser1.setBalance(1000.00);
        savingAccountUser1.setAbsoluteLimit(0.00);

        // Account current for user 2
        currentAccountUser2 = new Account();
        currentAccountUser2.setIBAN("NL00INHO0000000003");
        currentAccountUser2.setUser(user1);
        currentAccountUser2.setAccountType(Account.AccountTypeEnum.SAVINGS);
        currentAccountUser2.setBalance(1000.00);
        currentAccountUser2.setAbsoluteLimit(0.00);

        // Account current for employee 1
        AccountEmployee = new Account();
        AccountEmployee.setIBAN("NL00INHO0000000004");
        AccountEmployee.setUser(user1);
        AccountEmployee.setAccountType(Account.AccountTypeEnum.SAVINGS);
        AccountEmployee.setBalance(1000.00);
        AccountEmployee.setAbsoluteLimit(0.00);

        transaction1 = new Transaction();
        transaction1.setTransactionId(30);
        transaction1.setAccountFrom(currentAccountUser1.getIBAN());
        transaction1.setAccountTo(savingAccountUser1.getIBAN());
        transaction1.setPerformedBy(currentAccountUser1.getId().intValue());
        transaction1.setUser(currentAccountUser1.getUser());
        transaction1.timestamp(LocalDateTime.now().toString());
        transaction1.type(Transaction.TypeEnum.TRANSACTION);
        transaction1.setAmount(20.00);
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void newTransactionNotNull() {
        Assertions.assertNotNull(transaction1);
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionDataIsSet() {

        transaction1.setTransactionId(8);
        transaction1.setAccountFrom("NL00INHO0000000002");
        transaction1.setAccountTo("NL00INHO0000000003");
        transaction1.setAmount(100.00);
        transaction1.setPerformedBy(1);

        Assert.assertNotNull(transaction1);
        Assert.assertEquals(transaction1.getAccountFrom(), "NL00INHO0000000002");
        Assert.assertEquals(transaction1.getAccountTo(), "NL00INHO0000000003");
        Assert.assertEquals(transaction1.getAmount(),100.00, 0.001);
        Assert.assertEquals(transaction1.getPerformedBy(),1, 0.001);

    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionDataIsNotSet() {

        Transaction transaction = new Transaction();
        transaction.setAmount(25.50);
        transaction.setPerformedBy(1);

        Assert.assertNotNull(transaction);
        Assert.assertNull(transaction.getAccountFrom());
        Assert.assertNull(transaction.getAccountTo());

    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionIbanFromIsSet() {
        transaction1.setAccountFrom("NL00INHO0000000002");
        assertEquals("NL00INHO0000000002", transaction1.getAccountFrom());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionIbanToIsSet() {
        transaction1.setAccountTo("NL00INHO0000000002");
        assertEquals("NL00INHO0000000002", transaction1.getAccountTo());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionAmountToIsSet() {
        Double newBalance = 100.00;
        transaction1.setAmount(newBalance);
        assertEquals(newBalance, transaction1.getAmount());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    public void transactionUserIsSet() {
        transaction1.setUser(user1);
        assertEquals(user1, transaction1.getUser());
    }
}