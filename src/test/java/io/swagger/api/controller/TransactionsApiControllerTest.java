package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.threeten.bp.OffsetDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "MichaelEmployee",password = "welkom10", roles = "EMPLOYEE")
    public void getTransactionsShouldReturnSize2() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    //This user is an employee, so he has access to all transactions
    @Test
    @WithMockUser(username = "MichaelEmployee",password = "welkom10", roles = "EMPLOYEE")
    public void getSpecificTransactionWhenEmployeeShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions/7"))
                .andExpect(status().isOk());
    }

    //This user has  access to the transaction
    @Test
    @WithMockUser(username = "JasonUser",password = "welkom10", roles = "User")
    public void getSpecificTransactionWhenUserShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions/7"))
                .andExpect(status().isOk());
    }

    //This user has no access to the transaction
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "User")
    public void getSpecificTransactionWhenUserShouldNotReturnOk() throws Exception {
        this.mockMvc.perform(get("/transactions/8"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "User")
    public void createTransactionShouldReturnOk() throws Exception
    {
        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TypeEnum.TRANSACTION);
        transaction.setTimestamp(OffsetDateTime.now().toString());
        transaction.setAccountFrom("NL34INHO9915966699");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setPerformedBy(4);
        transaction.setAmount(5.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "User")
    public void createTransactionShouldNotReturnOk() throws Exception
    {
        // Create a new transaction
        Transaction transaction = new Transaction();
        transaction.setType(Transaction.TypeEnum.TRANSACTION);
        transaction.setTimestamp(OffsetDateTime.now().toString());
        transaction.setAccountFrom("NL34INHO9915966699");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setPerformedBy(4);
        transaction.setAmount(5.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}