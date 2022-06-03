package io.swagger.api.model.Entity;

import io.swagger.api.model.DTO.LoginDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginDTOTest {

    @Autowired
    private MockMvc mockMvc;

    private LoginDTO loginDetails = new LoginDTO();

    @BeforeEach
    public void setup() {
        loginDetails.setUsername("Frank");
        loginDetails.setPassword("test123");
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    void loginDetailsShouldNotBeNull() {
        Assertions.assertNotNull(loginDetails);
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    void usernameShouldNotBeNull() {
        Assertions.assertNotNull(loginDetails.getUsername());
    }

    @Test
    @WithMockUser(username = "Frank", password = "test", roles = "EMPLOYEE")
    void passwordShouldNotBeNull() {
        Assertions.assertNotNull(loginDetails.getPassword());
    }

    @Test
    @WithMockUser(username = "Frank",password = "test", roles = "EMPLOYEE")
    void loginDetailsContainsData() {
        assertNotNull(loginDetails);
        assertEquals("Frank", loginDetails.getUsername());
        assertEquals("test123", loginDetails.getPassword());
    }
}
