package io.swagger.api.model.Entity;

import io.swagger.api.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class UserTest {

    @Autowired
    private MockMvc mockMvc;

    private User user1;

    @BeforeEach
    public void setup() {
        user1 = new User();
        user1.setUsername("testuser1");
        user1.setPassword("welkom10");
        user1.setEmail("testuser1@mail.com");
        user1.setTransactionLimit(1000.00);
        user1.setDayLimit(10000.00);
        user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
        user1.setRoles(Arrays.asList(Role.ROLE_USER));
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    void newUserShouldNotBeNull() {
        Assertions.assertNotNull(user1);
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void userContainsData() {
        assertNotNull(user1);
        assertEquals("testuser1", user1.getUsername());
        assertEquals("welkom10", user1.getPassword());
        assertEquals("testuser1@mail.com", user1.getEmail());
        assertEquals((Double)1000.00, user1.getTransactionLimit());
        assertEquals((Double)10000.00, user1.getDayLimit());
        assertEquals(User.AccountStatusEnum.ACTIVE, user1.getAccountStatus());
        assertEquals(Arrays.asList(Role.ROLE_USER), user1.getRoles());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeUserNameShouldReturnOk(){
        String newUsername = "usertest12";
        user1.setUsername(newUsername);
        assertEquals(newUsername, user1.getUsername());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void changeEmailShouldReturnOk(){
        String newEmail = "usertest12@mail.com";
        user1.setEmail(newEmail);
        assertEquals(newEmail, user1.getEmail());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setTransactionLimitToPositiveNumberShouldSetThatTransactionLimit(){
        Double newTransactionLimit = 2000.00;
        user1.setTransactionLimit(newTransactionLimit);
        assertEquals(newTransactionLimit, user1.getTransactionLimit());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setTransactionLimitToNegativeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> user1.setTransactionLimit(-1.00));
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setDayLimitToPositiveNumberShouldSetThatDayLimit(){
        Double newDayLimit = 20000.00;
        user1.setDayLimit(newDayLimit);
        assertEquals(newDayLimit, user1.getDayLimit());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void setDayLimitToNegativeShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> user1.setDayLimit(-1.00));
    }
}