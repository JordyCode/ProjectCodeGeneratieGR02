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

        //This user is an employee
        User user1 = new User();
        user1.setUsername("MichaelEmployee");
        user1.setPassword("welkom10");
        user1.setFirstName("Michael");
        user1.setLastName("Employee");
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setDayLimit(500.00);
        user1.setTransactionLimit(100.00);
        user1.setEmail("test@mail.ml");
        user1.setDateOfBirth("01/01/2000");
        userService.add(user1, true);

        //This user is an employee
        User user2 = new User();
        user2.setUsername("JasonUser");
        user2.setPassword("welkom10");
        user2.setFirstName("Jason");
        user2.setLastName("User");
        user2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user2.setDayLimit(500.00);
        user2.setTransactionLimit(100.00);
        user2.setEmail("test@mail.nnl");;
        user2.setDateOfBirth("01/01/2001");
        userService.add(user2, false);

        //This account is a current account anf belongs to user2 MichaelEmployee
        Account account1 = new Account();
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setUser(user2);
        account1.setBalance(1500.00);
        account1.setAbsoluteLimit(-100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        accountService.add(account1, true);

        //This user is a customer
        User user3 = new User();
        user3.setUsername("FreddyUser");
        user3.setPassword("welkom10");
        user3.setFirstName("Freddy");
        user3.setLastName("User");
        user3.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user3.setDayLimit(200.00);
        user3.setTransactionLimit(1000.00);
        user3.setEmail("test@mail.ml");;
        user3.setDateOfBirth("01/01/2001");
        userService.add(user3, false);

        //This account is a current account and belongs to user2 FreddyUser
        Account account2 = new Account();
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setUser(user3);
        account2.setBalance(5000.00);
        account2.setIBAN("NL34INHO9915966699");
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account2.setAbsoluteLimit(-100.00);
        accountService.add(account2, false);

        //This account is a saving account and belongs to user2 FreddyUser
        Account account3 = new Account();
        account3.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account3.setUser(user3);
        account3.setBalance(1000.00);
        account3.setIBAN("NL53INHO4715545127");
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account3.setAbsoluteLimit(-100.00);
        accountService.add(account3, false);

        Transaction transaction1 = new Transaction();
        transaction1.setAccountTo(account2.getIBAN());
        transaction1.setAccountFrom(account1.getIBAN());
        transaction1.performedBy(user2.getUserId().intValue());
        transaction1.timestamp(LocalDateTime.now().toString());
        transaction1.type(Transaction.TypeEnum.TRANSACTION);
        transaction1.setAmount(30.00);
        transactionService.addTransaction(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setAccountTo(account2.getIBAN());
        transaction2.setAccountFrom(account3.getIBAN());
        transaction2.performedBy(user3.getUserId().intValue());
        transaction2.timestamp(LocalDateTime.now().toString());
        transaction2.type(Transaction.TypeEnum.TRANSACTION);
        transaction2.setAmount(500.00);
        transactionService.addTransaction(transaction2);
    }
}
