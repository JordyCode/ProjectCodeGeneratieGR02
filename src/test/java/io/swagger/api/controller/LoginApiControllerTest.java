package io.swagger.api.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.LoginDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import io.swagger.api.model.Entity.Transaction;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.threeten.bp.OffsetDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getLoginTokenAsAnEmployeeShouldReturnOk() throws Exception{
        LoginDTO loginDTO = new LoginDTO("EmployeeBank", "employee123");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/login")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token").value(startsWith("ey")))
                .andExpect(status().isOk());

    }

    @Test
    public void getLoginTokenAsAnEmployeeWithWrongDataShouldReturnNotOk() throws Exception{
        LoginDTO loginDTO = new LoginDTO("EmployeeBank", "test");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/login")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("Invalid username/password")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getLoginTokenAsUserThatIsInactiveNoDataShouldReturnNotOk() throws Exception{
        LoginDTO loginDTO = new LoginDTO("EmptyUser", "welkom10");
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/login")
                        .content(asJsonString(loginDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("An inactive user cannot login")))
                .andExpect(status().isBadRequest());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

