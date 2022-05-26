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

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User testUser = new User();
        testUser.setUsername("test123");
        testUser.setPassword("geheim");
        testUser.setFirstName("test");
        testUser.setLastName("tset");
        testUser.setEmail("test@tset.com");
        testUser.setDateOfBirth("03/03/1995");
        testUser.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));

        userService.add(testUser, true);

        Account account = new Account();
        account.setAccountType(Account.AccountTypeEnum.CURRENT);
        account.setUser(testUser);
        account.setBalance(150.00);
        account.setAbsoluteLimit(100.00);
        account.setAccountStatus(Account.AccountStatusEnum.ACTIVE);

        accountService.add(account, true);
    }
}
