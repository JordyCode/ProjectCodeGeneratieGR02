package io.swagger.configuration;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Account account1 = new Account();
        account1.setAccountType(Account.AccountTypeEnum.CURRENT);
        account1.setIBAN("NL00INHO000000001");
        account1.setBalance(150.00);
        account1.setAbsoluteLimit(100.00);
        account1.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        accountService.add(account1, false);

        User testUser = new User();
        testUser.setUserId(55555555L);
        testUser.setUsername("EmployeeBank");
        testUser.setPassword("employee123");
        testUser.setFirstName("Willem");
        testUser.setLastName("Wiltenburg");
        testUser.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        testUser.setDayLimit(200.00);
        testUser.setTransactionLimit(1000.00);
        testUser.setEmail("willem.wiltenburg@test.com");
        testUser.setDateOfBirth("03/03/19670");

        userService.add(testUser, true);

        // Create a new user
        User user2 = new User();
        user2.setUsername("UserBank");
        user2.setPassword("user123");
        user2.setFirstName("Frank");
        user2.setLastName("Dersjant");
        user2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user2.setDayLimit(200.00);
        user2.setTransactionLimit(1000.00);
        user2.setEmail("frank.dersjant@test.com");
        user2.setDateOfBirth("01/01/1970");
        userService.add(user2, false);

        // Create a new account
        Account account2 = new Account();
        account2.setAccountType(Account.AccountTypeEnum.CURRENT);
        account2.setUser(user2);
        account2.setBalance(500.00);
        account2.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account2.setAbsoluteLimit(100.00);
        accountService.add(account2, true);

        Account account3 = new Account();
        account3.setIBAN("NL00INHO000000003");
        account3.setAccountType(Account.AccountTypeEnum.SAVINGS);
        account3.setUser(user2);
        account3.setBalance(20000.00);
        account3.setAccountStatus(Account.AccountStatusEnum.ACTIVE);
        account3.setAbsoluteLimit(100.00);
        accountService.add(account3, false);
    }
}
