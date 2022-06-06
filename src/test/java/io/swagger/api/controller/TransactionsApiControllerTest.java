package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
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

    //This user is an employee, so he has access to all transactions
    //This only works when you run it alone, because if you run everything at ones is creates new transaction and expects 9 trans actions
//    @Test
//    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
//    public void getTransactionsAsEmployeeShouldReturnSizeOf5() throws Exception {
//        this.mockMvc.perform(get("/transactions"))
//                .andExpect(jsonPath("$", hasSize(5)));
//    }

    //READ ABOVE!! USE THIS WHEN YOU RUN THE CLASS ALL ONE
    //THIS IS FOR WHEN THE TEST CREATES NEW TRANSACTIONS, THE NEW TOTAL IS 9
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void getTransactionsAsEmployeeShouldReturnSizeOf9() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andExpect(jsonPath("$", hasSize(9)));
    }

    //This user is not an employee, so he only has access to his transactions
    @Test
    @WithMockUser(username = "UserBank",password = "user123", roles = "USER")
    public void getTransactionsAsUserShouldReturnSizeOf1() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    //This user is not an employee and has no transactions
    @Test
    @WithMockUser(username = "EmptyUser",password = "welkom10", roles = "USER")
    public void getTransactionsAsUserShouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andExpect(status().isBadRequest());
    }

    //This user is an employee, so he has access to specific transactions
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void getSpecificTransactionWhenEmployeeShouldReturnFound() throws Exception {
        this.mockMvc.perform(get("/transactions/13"))
                .andExpect(status().isFound());
    }

    //This user has  access to the transaction
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void getSpecificTransactionWhenUserShouldReturnFound() throws Exception {
        this.mockMvc.perform(get("/transactions/12"))
                .andExpect(status().isFound());
    }

    //This user has no access to the transaction
    @Test
    @WithMockUser(username = "EmptyUser",password = "welkom10", roles = "USER")
    public void getSpecificTransactionWhenUserShouldNotReturnFound() throws Exception {
        this.mockMvc.perform(get("/transactions/8"))
                .andExpect(status().isBadRequest());
    }

    //This account belongs to the FreddyUser from his current to saving's account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionAsUserShouldReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //This account belongs to the FreddyUser from his current to saving's account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionWhereAmountIsNegativeAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(-15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //This account belongs to the FreddyUser from his current to saving's account and is performed by an employee
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void createTransactionAsEmployeeShouldReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(20.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //This account belongs to the FreddyUser from his current to saving's account and is performed by an Empty User (with no access)
    @Test
    @WithMockUser(username = "EmptyUser",password = "welkom10", roles = "USER")
    public void createTransactionAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(5.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //This User wants to make a transaction but the transaction exceeds his daily limit
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionAsUserWhereUserExceedsTheTransactionLimitShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(503.0);
        //The daily transaction limit for this user is 500
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //User FreddyUser wants to make a deposit to his current account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createDepositIntoCurrentAsUserShouldReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //User FreddyUser wants to make a deposit to his saving's account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createDepositIntoSavingAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //User FreddyUser wants to make a deposit to his current account but the deposit amount is lower than 10 euros
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createDepositIntoCurrentWhereAmountIsLowerThan10EuroAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(9.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Employee wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void createDepositIntoCurrentAsEmployeeShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Empty user wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "EmptyUser",password = "welkom10", roles = "USER")
    public void createDepositIntoCurrentAsUserWithNoAccessShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //User FreddyUser wants to withdraw from his current account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createWithdrawtFromCurrentAsUserShouldReturnCreated() throws Exception
    {
        // Create a new transaction
        WithdrawTransactionDTO transaction = new WithdrawTransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //User FreddyUser wants to withdraw from his saving's account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createWithdrawFromSavingAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        WithdrawTransactionDTO transaction = new WithdrawTransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545127");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createWithdrawFromCurrentWhereAmountIsLowerThan10EuroAsUserShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        WithdrawTransactionDTO transaction = new WithdrawTransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAmount(9.00);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Employee wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void createWithdrawFromCurrentAsEmployeeShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        WithdrawTransactionDTO transaction = new WithdrawTransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //Empty user wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "EmptyUser",password = "welkom10", roles = "USER")
    public void createWithdrawFromCurrentAsUserWithNoAccessShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        WithdrawTransactionDTO transaction = new WithdrawTransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAmount(15.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString2(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString3(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}