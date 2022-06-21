package io.swagger.api.service;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionService transactionService;

    User user1 =  new User();
    User user2 =  new User();
    Account account1 =  new Account();
    Account account2 =  new Account();
    Account account3 =  new Account();

    @BeforeEach
    public void setup() {

        user1.setUserId(1L);
        user1.setAccounts(Arrays.asList(account1));
        user1.setFirstName("Frank");
        user1.setLastName("Test");
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setDateOfBirth("10/05/1976");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setUserStatus(User.UserStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));

        user2.setUsername("EmployeeBank");
        user2.setPassword("employee123");
        user2.setEmail("willem.wiltenburg@test.com");
        user2.setTransactionLimit(1000.00);
        user2.setDayLimit(200.00);
        user2.setUserStatus(User.UserStatusEnum.ACTIVE);
        user2.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));

        account1.setId(1L);
        account1.setIBAN("NL00INHO0000000002");
        account1.setUser(user1);
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setBalance(1000.00);
        account1.setAbsoluteLimit(50.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        account2.setId(2L);
        account2.setIBAN("NL00INHO0000000003");
        account2.setUser(user2);
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setBalance(500.00);
        account2.setAbsoluteLimit(100.00);
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        account3.setId(3L);
        account3.setIBAN("NL00INHO0000000004");
        account3.setUser(user1);
        account3.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account3.setBalance(1500.00);
        account3.setAbsoluteLimit(100.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

    }

    @Test
    void getAllAccountsGivesSize2(){
        doReturn(Arrays.asList(account1, account2)).when(accountRepository).findAll();
        List<Account> accounts = accountService.getAllAccounts();
        Assertions.assertEquals(2, accounts.size(), "The sizes should be the same");
        Assertions.assertEquals(accounts.get(0).getId(), account1.getId(), "The id's should be the same");
        Assertions.assertEquals(accounts.get(1).getId(), account2.getId(), "The id's should be the same");
        Assertions.assertEquals(accounts.get(1).getBalance(), account2.getBalance(), "The balances should be the same");
        Assertions.assertEquals(accounts.get(0).getAccountStatus(), account1.getAccountStatus(), "The status should be the same");
    }

    @Test
    void getAccountById(){
        doReturn(account1).when(accountRepository).getAccountById(1L);
        Account account = accountService.getAccountById(1L);
        Assertions.assertEquals(account.getId(), account1.getId(), "The id's should be the same");
        Assertions.assertEquals(account.getIBAN(), account1.getIBAN(), "The IBAN's should be the same");
        Assertions.assertEquals(account.getUser(), account1.getUser(), "The users should be the same");
    }

    @Test
    void checkIfAccountIsUser(){
        doReturn(true).when(accountRepository).existsByIdAndUser(1L, user1);
        boolean isAccount = accountService.checkIfAccountIsUser(1L, user1);
        Assertions.assertTrue(isAccount, "Account should belong to user");
    }

    @Test
    void getAccountsByUser(){
        doReturn(Arrays.asList(account2, account3)).when(accountRepository).getAccountByUser(user1);
        List<Account> accounts = accountService.getAccountsByUser(user1);
        Assertions.assertEquals(2, accounts.size(), "The sizes should be the same");
        Assertions.assertEquals(accounts.get(0).getId(), account2.getId(), "The id's should be the same");
        Assertions.assertEquals(accounts.get(1).getId(), account3.getId(), "The id's should be the same");
        Assertions.assertEquals(accounts.get(1).getAbsoluteLimit(), account3.getAbsoluteLimit(), "The limits should be the same");
        Assertions.assertEquals(accounts.get(0).getAccountType(), account3.getAccountType(), "The account types should be the same");
    }

    @Test
    void existByIBAN(){
        doReturn(true).when(accountRepository).existsByIBAN("NL00INHO0000000002");
        boolean isAccount = accountService.existByIBAN("NL00INHO0000000002");
        Assertions.assertTrue(isAccount, "Account should exist");
    }

    @Test
    void addAccount(){
        doReturn(account2).when(accountRepository).save(account2);
        Account account = accountService.add(account2, false);
        Assertions.assertNotNull(account, "Account can not be null");
        Assertions.assertEquals(account.getIBAN(), account2.getIBAN(), "The IBAN's should be the same");
        Assertions.assertEquals(account.getAbsoluteLimit(), account2.getAbsoluteLimit(), "The limits should be the same");
        Assertions.assertEquals(account.getId(), account2.getId(), "The id's should be the same");
    }

}
