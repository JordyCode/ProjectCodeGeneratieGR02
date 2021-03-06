package io.swagger.configuration;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private UserService userService;
  
    @Autowired
    private AccountService accountService;
  
    @Autowired
    private TransactionService transactionService;

    @Value("${bank.iban}")
    private String bankIban;

    public MyApplicationRunner(UserService userService, AccountService accountService, TransactionService transactionService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //Create an account for the bank, this account will be hidden for all employees.
        Account bankAccount = new Account();
        bankAccount.setId(1L);
        bankAccount.setAccountType(Account.AccountTypeEnum.CURRENT);
        bankAccount.setIBAN(bankIban);
        bankAccount.setBalance(150000000.00);
        bankAccount.setAbsoluteLimit(-100000000.00);
        bankAccount.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        accountService.add(bankAccount, false);

        User emptyUser2 = new User();
        emptyUser2.setUsername("emptyUser2");
        emptyUser2.setPassword("welkom12");
        emptyUser2.setFirstName("user");
        emptyUser2.setLastName("empty");
        emptyUser2.setUserStatus(User.UserStatusEnum.ACTIVE);
        emptyUser2.setDayLimit(0.0);
        emptyUser2.setTransactionLimit(0.0);
        emptyUser2.setEmail("empty@empty.nl");
        emptyUser2.setDateOfBirth("01/01/1900");
        userService.add(emptyUser2, false);

        // Create a new employee
        User testUser1 = new User();
        testUser1.setUserId(3L);
        testUser1.setUsername("EmployeeBank");
        testUser1.setPassword("employee123");
        testUser1.setFirstName("Willem");
        testUser1.setLastName("Wiltenburg");
        testUser1.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser1.setDayLimit(100.00);
        testUser1.setTransactionLimit(1000.00);
        testUser1.setEmail("willem.wiltenburg@test.com");
        testUser1.setDateOfBirth("03/03/19670");
        userService.add(testUser1, true);

        //This user is a customer with no transactions or accounts (Will be used for testing only
        User emptyUser = new User();
        emptyUser.setUsername("EmptyUser");
        emptyUser.setPassword("welkom10");
        emptyUser.setFirstName("Empty");
        emptyUser.setLastName("UserEmpty");
        emptyUser.setUserStatus(User.UserStatusEnum.INACTIVE);
        emptyUser.setDayLimit(1000.00);
        emptyUser.setTransactionLimit(500.00);
        emptyUser.setEmail("test@mail.ml");;
        emptyUser.setDateOfBirth("01/01/2001");
        userService.add(emptyUser, false);

        // Create a new user
        User testUser2 = new User();
        testUser2.setUserId(5L);
        testUser2.setUsername("UserBank");
        testUser2.setPassword("user123");
        testUser2.setFirstName("Frank");
        testUser2.setLastName("Dersjant");
        testUser2.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser2.setDayLimit(700.00);
        testUser2.setTransactionLimit(500.00);
        testUser2.setEmail("frank.dersjant@test.com");
        testUser2.setDateOfBirth("01/01/1970");
        userService.add(testUser2, false);

        // Create a current new account for testUser2 UserBank
        Account account2 = new Account();
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setUser(testUser2);
        account2.setBalance(500.00);
        account2.setIBAN("NL53INHO4715545129");
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account2.setAbsoluteLimit(-100.00);
        accountService.add(account2, false);

        //This account is a saving account and belongs to user2 UserBank DO NOT USE!!!
        Account inactiveAccount = new Account();
        inactiveAccount.setAccountType(Account.AccountTypeEnum.SAVINGS);
        inactiveAccount.setUser(testUser2);
        inactiveAccount.setBalance(0.00);
        inactiveAccount.setAccountStatus(Account.AccountStatusEnum.INACTIVE);
        inactiveAccount.setAbsoluteLimit(0.0);
        accountService.add(inactiveAccount, true);

        //This user is a customer
        User testUser3 = new User();
        testUser3.setUserId(8L);
        testUser3.setUsername("FreddyUser");
        testUser3.setPassword("welkom10");
        testUser3.setFirstName("Freddy");
        testUser3.setLastName("User3");
        testUser3.setUserStatus(User.UserStatusEnum.ACTIVE);
        testUser3.setDayLimit(600.00);
        testUser3.setTransactionLimit(400.00);
        testUser3.setEmail("test@mail.ml");;
        testUser3.setDateOfBirth("01/01/2001");
        userService.add(testUser3, false);

        //This account is a current account and belongs to user3 FreddyUser
        Account account3 = new Account();
        account3.setAccountType(Account.AccountTypeEnum.CURRENT);
        account3.setUser(testUser3);
        account3.setIBAN("NL53INHO4715545128");
        account3.setBalance(500.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account3.setAbsoluteLimit(-100.00);
        accountService.add(account3, false);

        //This account is a saving's account and belongs to user3 FreddyUser
        Account account4 = new Account();
        account4.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account4.setUser(testUser3);
        account4.setBalance(1000.00);
        account4.setIBAN("NL53INHO4715545127");
        account4.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account4.setAbsoluteLimit(-100.00);
        accountService.add(account4, false);

        Account account5 = new Account();
        account5.setAccountType(Account.AccountTypeEnum.CURRENT);
        account5.setUser(testUser1);
        account5.setBalance(500.00);
        account5.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account5.setAbsoluteLimit(-100.00);
        accountService.add(account5, true);

        //A transaction from testuser2 to testuser3 both current account and performed by testUser2
        Transaction transaction1 = new Transaction();
        transaction1.setAccountTo(account2.getIBAN());
        transaction1.setAccountFrom(account3.getIBAN());
        transaction1.performedBy(testUser3.getUserId().intValue());
        transaction1.timestamp(LocalDateTime.now().toString());
        transaction1.type(Transaction.TypeEnum.TRANSACTION);
        transaction1.setAmount(25.00);
        transaction1.setUser(testUser3);
        transactionService.addTransaction(transaction1);

        //A transaction from saving account to current account both belonging to testUser3 and performed by testUser3
        Transaction transaction2 = new Transaction();
        transaction2.setAccountTo(account3.getIBAN());
        transaction2.setAccountFrom(account4.getIBAN());
        transaction2.performedBy(testUser3.getUserId().intValue());
        transaction2.timestamp(LocalDateTime.now().toString());
        transaction2.type(Transaction.TypeEnum.TRANSACTION);
        transaction2.setAmount(50.00);
        transaction2.setUser(account3.getUser());
        transactionService.addTransaction(transaction2);

        // A transaction from testUser3 to testUser2 both current account and performed by testUser1 (which is an employee)
        Transaction transaction3 = new Transaction();
        transaction3.setAccountTo(account2.getIBAN());
        transaction3.setAccountFrom(account3.getIBAN());
        transaction3.performedBy(testUser1.getUserId().intValue());
        transaction3.timestamp(LocalDateTime.now().toString());
        transaction3.type(Transaction.TypeEnum.TRANSACTION);
        transaction3.setAmount(330.00);
        transaction3.setUser(account3.getUser());
        transactionService.addTransaction(transaction3);

        // A deposit to testUser3's current account and performed by testUser3 (which is the only one who can make a deposit to this account)
        Transaction depositTransaction = new Transaction();
        depositTransaction.setAccountTo(account3.getIBAN());
        depositTransaction.setAccountFrom(bankIban);
        depositTransaction.performedBy(testUser3.getUserId().intValue());
        depositTransaction.timestamp(LocalDateTime.now().toString());
        depositTransaction.type(Transaction.TypeEnum.DEPOSIT);
        depositTransaction.setAmount(20.00);
        depositTransaction.setUser(testUser3);
        transactionService.addDepositTransaction(depositTransaction);

        // A withdraw from testUser3's current account and performed by testUser3 (which is the only one who can make a withdraw from this account)
        Transaction withdrawTransaction = new Transaction();
        withdrawTransaction.setAccountTo(bankIban);
        withdrawTransaction.setAccountFrom(account3.getIBAN());
        withdrawTransaction.performedBy(testUser3.getUserId().intValue());
        withdrawTransaction.timestamp(LocalDateTime.now().toString());
        withdrawTransaction.type(Transaction.TypeEnum.WITHDRAW);
        withdrawTransaction.setAmount(30.00);
        withdrawTransaction.setUser(testUser3);
        transactionService.addWithdrawTransaction(withdrawTransaction);
    }
}

