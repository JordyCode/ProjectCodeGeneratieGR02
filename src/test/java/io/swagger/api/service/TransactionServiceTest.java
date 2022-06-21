package io.swagger.api.service;

import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class TransactionServiceTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    User user1 =  new User();
    User user2 =  new User();
    Account account1 =  new Account();
    Account account2 =  new Account();
    Account account3 =  new Account();

    Transaction transactionTest1 = new Transaction();

    Transaction transactionTest2 = new Transaction();

    Transaction transactionTest3 = new Transaction();

    WithdrawTransactionDTO withdrawTransactionTest1 = new WithdrawTransactionDTO();

    WithdrawTransactionDTO withdrawTransactionTest2 = new WithdrawTransactionDTO();

    DepositTransactionDTO depositTransactionTest1 = new DepositTransactionDTO();

    DepositTransactionDTO depositTransactionTest2 = new DepositTransactionDTO();


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

        user2.setUserId(2L);
        user2.setAccounts(Arrays.asList(account2,account3));
        user2.setFirstName("Frank2");
        user2.setLastName("Test2");
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
        account3.setUser(user2);
        account3.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account3.setBalance(1500.00);
        account3.setAbsoluteLimit(100.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        transactionTest1.setTransactionId(23);
        transactionTest1.setAccountFrom(account1.getIBAN());
        transactionTest1.setAccountTo(account2.getIBAN());
        transactionTest1.setPerformedBy(account1.getId().intValue());
        transactionTest1.setUser(account1.getUser());
        transactionTest1.timestamp(LocalDateTime.now().toString());
        transactionTest1.type(Transaction.TypeEnum.TRANSACTION);
        transactionTest1.setAmount(20.00);

        transactionTest2.setTransactionId(24);
        transactionTest2.setAccountFrom(account2.getIBAN());
        transactionTest2.setAccountTo(account1.getIBAN());
        transactionTest2.setPerformedBy(account2.getId().intValue());
        transactionTest2.setUser(account2.getUser());
        transactionTest2.timestamp(LocalDateTime.now().toString());
        transactionTest2.type(Transaction.TypeEnum.TRANSACTION);
        transactionTest2.setAmount(30.00);

        transactionTest3.setTransactionId(25);
        transactionTest3.setAccountFrom(account2.getIBAN());
        transactionTest3.setAccountTo(account3.getIBAN());
        transactionTest3.setPerformedBy(account2.getId().intValue());
        transactionTest3.setUser(account2.getUser());
        transactionTest3.timestamp(LocalDateTime.now().toString());
        transactionTest3.type(Transaction.TypeEnum.TRANSACTION);
        transactionTest3.setAmount(450.00);

        withdrawTransactionTest1.setAccountFrom(account2.getIBAN());
        withdrawTransactionTest1.setPerformedBy(account2.getId().intValue());
        withdrawTransactionTest1.setAmount(10.00);

        depositTransactionTest1.setAccountTo(account2.getIBAN());
        depositTransactionTest1.setPerformedBy(account2.getId().intValue());
        depositTransactionTest1.setAmount(10.00);

    }

    @Test
    void getAllTransactionsGivesSize3(){
        doReturn(Arrays.asList(transactionTest1, transactionTest2, transactionTest3)).when(transactionRepository).findAll();
        List<Transaction> transactions = transactionService.getAllTransactions();
        Assertions.assertEquals(3, transactions.size(), "The sizes should be the same");
        Assertions.assertEquals(transactions.get(0).getTransactionId(), transactionTest1.getTransactionId(), "The id's should be 23");
        Assertions.assertEquals(transactions.get(1).getTransactionId(), transactionTest2.getTransactionId(), "The id's should be 24");
        Assertions.assertEquals(transactions.get(2).getTransactionId(), transactionTest3.getTransactionId(), "The id's should be 25");
        Assertions.assertEquals(transactions.get(0).getAmount(), transactionTest1.getAmount(), "The amount 20.00");
        Assertions.assertEquals(transactions.get(1).getAmount(), transactionTest2.getAmount(), "The amount 30.00");
        Assertions.assertEquals(transactions.get(2).getAmount(), transactionTest3.getAmount(), "The amount 450.00");
        Assertions.assertEquals(transactions.get(0).getUser(), user1, "Performed by User1");
        Assertions.assertEquals(transactions.get(1).getUser(), transactionTest2.getUser(), "Performed by User2");
        Assertions.assertEquals(transactions.get(2).getUser(), transactionTest3.getUser(), "Performed by User2");

    }

    @Test
    void getAllUserTransactionsGivesSize2(){
        doReturn(Arrays.asList(transactionTest2, transactionTest3)).when(transactionRepository).getTransactionByUser(user2);
        List<Transaction> transactions = transactionService.getAllUserTransactions(user2);
        Assertions.assertEquals(2, transactions.size(), "The sizes should be the same");
        Assertions.assertEquals(transactions.get(0).getTransactionId(), transactionTest2.getTransactionId(), "The id's should be 24");
        Assertions.assertEquals(transactions.get(1).getTransactionId(), transactionTest3.getTransactionId(), "The id's should be 25");
        Assertions.assertEquals(transactions.get(0).getUser(), transactionTest2.getUser(), "This user should be user2");
        Assertions.assertEquals(transactions.get(1).getAmount(), transactionTest3.getAmount(), "The amount 30.00");
        Assertions.assertEquals(transactions.get(0).getUser(), user2, "Performed by User1");
        Assertions.assertEquals(transactions.get(1).getUser(), transactionTest3.getUser(), "Performed by User2");

    }

    @Test
    void getTransactionsById(){
        doReturn(transactionTest1).when(transactionRepository).findTransactionByTransactionId(transactionTest1.getTransactionId());
        Transaction transaction = transactionService.getTransactionsById(transactionTest1.getTransactionId());
        Assertions.assertEquals(transaction.getTransactionId(), transactionTest1.getTransactionId(), "The id's should be the same");
        Assertions.assertEquals(transaction.getAccountFrom(), transactionTest1.getAccountFrom(), "The IBAN's should be the same");
        Assertions.assertEquals(transaction.getUser(), user1, "Performed by User1");
        Assertions.assertEquals(transaction.getAccountTo(), account2.getIBAN(), "The IBAN's should be the same");

    }

    @Test
    void checkIfTransactionBelongsToUser(){
        doReturn(true).when(transactionRepository).existsByUserAndTransactionId(user1, transactionTest1.getTransactionId());
        boolean transactionBelongsToUser = transactionService.checkIfTransactionBelongsToUser(user1, transactionTest1.getTransactionId());
        Assertions.assertTrue(transactionBelongsToUser, "Transactions belongs to user");
    }

}

