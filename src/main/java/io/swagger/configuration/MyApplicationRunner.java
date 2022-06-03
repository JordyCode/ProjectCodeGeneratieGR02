package io.swagger.configuration;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private UserService userService;
    private AccountService accountService;
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public MyApplicationRunner(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //Create a user for the Bank's account, because according to the user story every account must have a user. (It was not stated that the bank's own account should not have a user attached)
        User bankUser = new User();
        bankUser.setUsername("BANK");
        bankUser.setPassword("welkom10");
        bankUser.setFirstName("BANK");
        bankUser.setLastName("ACCOUNT");
        bankUser.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        bankUser.setDayLimit(100000000.00);
        bankUser.setTransactionLimit(100000000.00);
        bankUser.setEmail("Bank");
        bankUser.setDateOfBirth("01/01/1900");
        userService.add(bankUser, true);

        //Create an account for the bank, this account will be hidden for all employees.
        Account account1 = new Account();
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setIBAN("NL00INHO000000001");
        account1.setUser(bankUser);
        account1.setBalance(150000000.00);
        account1.setAbsoluteLimit(-100000000.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        accountService.add(account1, false);

        // Create a new employee
        User testUser1 = new User();
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");
        userService.add(testUser1, true);

        // Create a new user
        User testUser2 = new User();
        testUser2.setUsername("UserBank");
        testUser2.setPassword("user123");
        testUser2.setFirstName("Frank");
        testUser2.setLastName("Dersjant");
        testUser2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser2.setDayLimit(1000.00);
        testUser2.setTransactionLimit(500.00);
        testUser2.setEmail("frank.dersjant@test.com");
        testUser2.setDateOfBirth("01/01/1970");
        userService.add(testUser2, false);

        //This user is a customer
        User testUser3 = new User();
        testUser3.setUsername("FreddyUser");
        testUser3.setPassword("welkom10");
        testUser3.setFirstName("Freddy");
        testUser3.setLastName("User");
        testUser3.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser3.setDayLimit(1000.00);
        testUser3.setTransactionLimit(500.00);
        testUser3.setEmail("test@mail.ml");;
        testUser3.setDateOfBirth("01/01/2001");
        userService.add(testUser3, false);


        // Create a new account
        Account account2 = new Account();
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setUser(testUser2);
        account2.setBalance(500.00);
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account2.setAbsoluteLimit(-100.00);
        accountService.add(account2, true);

        // Create a new account
        Account account3 = new Account();
        account3.setAccountType(Account.AccountTypeEnum.CURRENT);
        account3.setUser(testUser3);
        account3.setBalance(500.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account3.setAbsoluteLimit(-100.00);
        accountService.add(account3, true);

        //This account is a saving's account and belongs to user3 FreddyUser
        Account account4 = new Account();
        account4.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account4.setUser(testUser3);
        account4.setBalance(1000.00);
        account4.setIBAN("NL53INHO4715545127");
        account4.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account4.setAbsoluteLimit(-100.00);
        accountService.add(account4, false);

        //A transaction from testuser2 to testuser3 both current account and performed by testuser2
        Transaction transaction1 = new Transaction();
        transaction1.setAccountTo(account3.getIBAN());
        transaction1.setAccountFrom(account2.getIBAN());
        transaction1.performedBy(testUser2.getUserId().intValue());
        transaction1.timestamp(LocalDateTime.now().toString());
        transaction1.type(Transaction.TypeEnum.TRANSACTION);
        transaction1.setAmount(25.00);
        transactionService.addTransaction(transaction1);

        Transaction transactiontest = new Transaction();
        transactiontest.setAccountTo(account3.getIBAN());
        transactiontest.setAccountFrom(account2.getIBAN());
        transactiontest.performedBy(testUser2.getUserId().intValue());
        transactiontest.timestamp(LocalDateTime.now().toString());
        transactiontest.type(Transaction.TypeEnum.TRANSACTION);
        transactiontest.setAmount(170.00);
        transactionService.addTransaction(transactiontest);

        //A transaction from saving account to current account both belonging to testuser3 and performed by testuser3
        Transaction transaction2 = new Transaction();
        transaction2.setAccountTo(account3.getIBAN());
        transaction2.setAccountFrom(account4.getIBAN());
        transaction2.performedBy(testUser3.getUserId().intValue());
        transaction2.timestamp(LocalDateTime.now().toString());
        transaction2.type(Transaction.TypeEnum.TRANSACTION);
        transaction2.setAmount(50.00);
        transactionService.addTransaction(transaction2);

        // A transaction from testuser3 to testuser2 both current account and performed by testuser1 (which is an employee)
        Transaction transaction3 = new Transaction();
        transaction3.setAccountTo(account2.getIBAN());
        transaction3.setAccountFrom(account3.getIBAN());
        transaction3.performedBy(testUser1.getUserId().intValue());
        transaction3.timestamp(LocalDateTime.now().toString());
        transaction3.type(Transaction.TypeEnum.TRANSACTION);
        transaction3.setAmount(33.00);
        transactionService.addTransaction(transaction3);
    }
}

