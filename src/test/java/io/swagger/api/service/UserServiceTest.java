package io.swagger.api.service;

import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.UserRepository;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionService transactionService;

    User user1 =  new User();
    User user2 =  new User();
    User user3 =  new User();
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

        user2.setUserId(2L);
        user2.setUsername("EmployeeBank");
        user2.setPassword("employee123");
        user2.setFirstName("Willem");
        user2.setLastName("Wiltenburg");
        user2.setEmail("willem.wiltenburg@test.com");
        user2.setTransactionLimit(1000.00);
        user2.setDayLimit(200.00);
        user2.setUserStatus(User.UserStatusEnum.ACTIVE);
        user2.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));

        user3.setUserId(3L);
        user3.setUsername("PieterJan");
        user3.setPassword("test123");
        user3.setFirstName("Pieter");
        user3.setLastName("Burg");
        user3.setEmail("Pieter@live.com");
        user3.setTransactionLimit(2000.00);
        user3.setDayLimit(250.00);
        user3.setUserStatus(User.UserStatusEnum.ACTIVE);
        user3.setRoles(Arrays.asList(Role.ROLE_USER));

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
    void getAllUsersGivesSize2(){
        doReturn(Arrays.asList(user1, user2)).when(userRepository).findAll();
        List<User> users = userService.getAllUsers();
        Assertions.assertEquals(2, users.size(), "The sizes should be 2");
        Assertions.assertEquals(users.get(0).getUserId(), user1.getUserId(), "The id's should be 1");
        Assertions.assertEquals(users.get(1).getUserId(), user2.getUserId(), "The id's should be 2");
        Assertions.assertEquals(users.get(1).getFirstName(), user2.getFirstName(), "The firstnames should be Willem");
        Assertions.assertEquals(users.get(0).getDayLimit(), user1.getDayLimit(), "The day limits should be 10000.00");
        Assertions.assertEquals(users.get(0).getUserStatus(), user1.getUserStatus(), "The status should be active");
        Assertions.assertEquals(users.get(1).getUserStatus(), user2.getUserStatus(), "The status should be active");
    }

    @Test
    void getSpecificUser(){
        doReturn(user1).when(userRepository).getUserByUserId(1L);
        User user = userService.getSpecificUser(1L);
        Assertions.assertEquals(user.getUserId(), user1.getUserId(), "The id's should be 1");
        Assertions.assertEquals(user.getFirstName(), user1.getFirstName(), "The firstnames should be Frank");
        Assertions.assertEquals(user.getLastName(), user1.getLastName(), "The lastnames should be Test");
        Assertions.assertEquals(user.getUsername(), user1.getUsername(), "The usernames should be testuser1");
        Assertions.assertEquals(user.getTransactionLimit(), user1.getTransactionLimit(), "The transaction limits should be 1000.00");
    }

    @Test
    void findByUsername(){
        doReturn(user1).when(userRepository).findByUsername("Frank");
        User user = userService.findByUsername("Frank");
        Assertions.assertEquals(user.getFirstName(), user1.getFirstName(), "The firstnames should be Frank");
        Assertions.assertEquals(user.getLastName(), user1.getLastName(), "The lastnames should be Test");
        Assertions.assertEquals(user.getEmail(), user1.getEmail(), "The emails should be testuser1@mail.com");
    }

    @Test
    void getUsersByAccountsIsNull(){
        doReturn(Arrays.asList(user2, user3)).when(userRepository).getUsersByAccountsIsNull();
        List<User> usersWithoutAccount = userService.getUsersByAccountsIsNull();
        Assertions.assertEquals(2, usersWithoutAccount.size(), "The sizes should be 2");
        Assertions.assertEquals(usersWithoutAccount.get(0).getUserId(), user2.getUserId(), "The id's should be 2");
        Assertions.assertEquals(usersWithoutAccount.get(1).getUserId(), user3.getUserId(), "The id's should be 3");
        Assertions.assertEquals(usersWithoutAccount.get(1).getUserStatus(), user3.getUserStatus(), "The status should be active");
        Assertions.assertEquals(usersWithoutAccount.get(1).getEmail(), user3.getEmail(), "The emails should be Pieter@live.com");
        Assertions.assertEquals(usersWithoutAccount.get(0).getEmail(), user2.getEmail(), "The emails should be willem.wiltenburg@test.com");
    }

    @Test
    void addUser(){
        doReturn(user1).when(userRepository).save(user1);
        User user = userService.add(user1, false);
        Assertions.assertNotNull(user, "User can not be null");
        Assertions.assertEquals(user.getEmail(), user1.getEmail(), "The emails should be testuser1@mail.com");
        Assertions.assertEquals(user.getUserId(), user1.getUserId(), "The id's should be 1");
        Assertions.assertEquals(user.getUsername(), user1.getUsername(), "The usernames should be testuser1");
        Assertions.assertEquals(user.getDayLimit(), user1.getDayLimit(), "The day limits should be 10000.00");
    }
}
