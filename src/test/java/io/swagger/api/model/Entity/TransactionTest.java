package io.swagger.api.model.Entity;

import io.swagger.api.model.Role;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private User user1;
    private User user2;
    private User employee1;
    private Account currentAccountUser1;
    private Account savingAccountUser1;
    private Account currentAccountUser2;
    private Account AccountEmployee;

    @BeforeEach
    public void prepareRefData(){

        // User
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        user2 = new User();
        user2.setUsername("testuser2");
        user2.setPassword("welkom10");
        user2.setEmail("testuser2@mail.com");
        user2.setTransactionLimit(1000.00);
        user2.setDayLimit(10000.00);
        user2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user2.setRoles(Arrays.asList(Role.ROLE_USER));

        employee1 = new User();
        employee1.setUsername("testuser3");
        employee1.setPassword("welkom10");
        employee1.setEmail("testuser3@mail.com");
        employee1.setTransactionLimit(10000.00);
        employee1.setDayLimit(100000.00);
        employee1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        employee1.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));

        // Account current for user 1
        currentAccountUser1 = new Account();
        currentAccountUser1.setIBAN("NL00INHO0000000002");
        currentAccountUser1.setUser(user1);
        currentAccountUser1.setAccountType(Account.AccountTypeEnum.CURRENT);
        currentAccountUser1.setBalance(1000.00);
        currentAccountUser1.setAbsoluteLimit(-500.00);

        // Account savings for user 1
        savingAccountUser1 = new Account();
        savingAccountUser1.setIBAN("NL00INHO0000000003");
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
        AccountEmployee.setIBAN("NL00INHO0000000003");
        AccountEmployee.setUser(user1);
        AccountEmployee.setAccountType(Account.AccountTypeEnum.SAVINGS);
        AccountEmployee.setBalance(1000.00);
        AccountEmployee.setAbsoluteLimit(0.00);
    }

    @Test
    public void newTransactionNotNull() {
        Transaction transaction = new Transaction();
        Assert.assertNotNull(transaction);
    }

    @Test
    public void userDataIsSet() {

        Transaction transaction = new Transaction();
        transaction.setTransactionId(8);
        transaction.setAccountFrom("NL00INHO0000000002");
        transaction.setAccountTo("NL00INHO0000000003");
        transaction.setAmount(100.00);
        transaction.setPerformedBy(1);

        Assert.assertNotNull(transaction);
        Assert.assertEquals(transaction.getAccountFrom(), "NL00INHO0000000002");
        Assert.assertEquals(transaction.getAccountTo(), "NL00INHO0000000003");
        Assert.assertEquals(transaction.getAmount(),100.00, 0.001);
        Assert.assertEquals(transaction.getPerformedBy(),1, 0.001);

    }

    @Test
    public void userTransactionAccountNotSet() {

        Transaction transaction = new Transaction();
        transaction.setAmount(25.50);
        transaction.setPerformedBy(1);

        Assert.assertNotNull(transaction);
        Assert.assertNull(transaction.getAccountFrom());
        Assert.assertNull(transaction.getAccountTo());

    }
}