package io.swagger.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest{

    @Autowired
    private MockMvc mockMvc;

    //This account belongs to the FreddyUser from his current to saving's account
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionAsUserShouldReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        transaction.setAmount(34.23);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Transaction.transactionId").value(19))
                .andExpect(jsonPath("$.Transaction.amount").value(34.23))
                .andExpect(jsonPath("$.Transaction.accountfrom").value("NL53INHO4715545128"))
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
                .andExpect(jsonPath("$").value(containsString("Zero or negative amounts are not allowed!")))
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

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.transactionId").value(20))
                .andExpect(jsonPath("$.amount").value(20.0))
                .andExpect(jsonPath("$.accountto").value("NL53INHO4715545127"))
                .andExpect(status().isCreated());
    }

    //This account belongs to the FreddyUser from his current to saving's account and is performed by an Empty User (with no access)
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
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
                .andExpect(jsonPath("$").value(containsString("User is not allowed to transfer money from another user's account")))
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
                .andExpect(jsonPath("$").value(containsString("Transaction amount exceeds transaction limit for user!")))
                .andExpect(status().isBadRequest());
    }

    //This User wants to make a transaction but the transaction exceeds his daily limit
    @Test
    @WithMockUser(username = "UserBank",password = "user123", roles = "USER")
    public void createTransactionAsUserWithLowFundsShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545129");
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(40.0);
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("Insufficient funds!")))
                .andExpect(status().isBadRequest());
    }

    //TBC
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionAsUserWhereAmountIsEmptyShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545127");
        //The transaction amount has not been set

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("Transaction amount has not been filled")))
                .andExpect(status().isBadRequest());
    }

    //This User wants to make a transaction but the receiver's IBAN does not exist
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void createTransactionWithIbanThatExistDoesNotShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545128");
        transaction.setAccountTo("NL53INHO4715545145");
        transaction.setAmount(20.0);
        //The ReceiverIban does not exist (SetAccountTo)
        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("The receiver IBAN does not exist!")))
                .andExpect(status().isBadRequest());
    }

    //This User wants to make a transaction but the sender's IBAN has been closed
    @Test
    @WithMockUser(username = "UserBank",password = "user123", roles = "USER")
    public void createTransactionWithSenderIbanThatHasBeenClosedShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        //The sender Iban Has been closed
        transaction.setAccountFrom("NL53INHO4715545130");
        transaction.setAccountTo("NL53INHO4715545129");
        transaction.setAmount(20.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("The sender's account has been closed!")))
                .andExpect(status().isBadRequest());
    }

    //This User wants to make a transaction but the sender's IBAN has been closed
    @Test
    @WithMockUser(username = "UserBank",password = "user123", roles = "USER")
    public void createTransactionWithReceiverIbanThatHasBeenClosedShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountFrom("NL53INHO4715545129");
        transaction.setAccountTo("NL53INHO4715545130");
        //The receiver Iban Has been closed
        transaction.setAmount(20.0);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("The receiver's account has been closed!")))
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

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Transaction.transactionId").value(18))
                .andExpect(jsonPath("$.Transaction.amount").value(15.0))
                .andExpect(jsonPath("$.Transaction.accountto").value("NL53INHO4715545128"))
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
                .andExpect(jsonPath("$").value(containsString("Cannot deposit into a saving's account")))
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
                .andExpect(jsonPath("$").value(containsString("Cannot deposit an amount lower than 10 euro")))
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

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("Only the user who the account belongs to is allowed to deposit")))
                .andExpect(status().isBadRequest());
    }

    //Empty user wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
    public void createDepositIntoCurrentAsUserWithNoAccessShouldNotReturnCreated() throws Exception
    {
        // Create a new transaction
        DepositTransactionDTO transaction = new DepositTransactionDTO();
        transaction.setAccountTo("NL53INHO4715545128");
        transaction.setAmount(15.0);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/deposit")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(containsString("Only the user who the account belongs to is allowed to deposit")))
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
        transaction.setAmount(40.0);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/transactions/withdraw")
                        .content(asJsonString(transaction))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Transaction.transactionId").value(17))
                .andExpect(jsonPath("$.Transaction.accountfrom").value("NL53INHO4715545128"))
                .andExpect(jsonPath("$.Transaction.amount").value(40.0))
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
                .andExpect(jsonPath("$").value(containsString("Cannot withdraw from a saving's account")))
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
                .andExpect(jsonPath("$").value(containsString("Cannot withdraw an amount lower than 10 euro")))
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
                .andExpect(jsonPath("$").value(containsString("Only the user who the account belongs to is allowed to withdraw")))
                .andExpect(status().isBadRequest());
    }

    //Empty user wants to make a deposit to the current account of FreddyUser
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
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
                .andExpect(jsonPath("$").value(containsString("Only the user who the account belongs to is allowed to withdraw")))
                .andExpect(status().isBadRequest());
    }

    //This user is not an employee and has no transactions
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
    public void getTransactionsAsUserShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/transactions"))
                .andExpect(jsonPath("$").value(containsString("No user transactions found")))
                .andExpect(status().isBadRequest());
    }

    //This user is an employee, so he has access to specific transactions
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void getSpecificTransactionWhenEmployeeShouldReturnFound() throws Exception {
        mockMvc.perform(get("/transactions/13"))
                .andExpect(jsonPath("$.transactionId").value(13))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.accountfrom").value("NL53INHO4715545127"))
                .andExpect(status().isOk());
    }

    //This user has  access to the transaction
    @Test
    @WithMockUser(username = "FreddyUser",password = "welkom10", roles = "USER")
    public void getSpecificTransactionWhenUserShouldReturnFound() throws Exception {
        mockMvc.perform(get("/transactions/12"))
                .andExpect(jsonPath("$.transactionId").value(12))
                .andExpect(jsonPath("$.amount").value(25.0))
                .andExpect(jsonPath("$.accountto").value("NL53INHO4715545129"))
                .andExpect(status().isOk());
    }

    //This user has no access to the transaction and the transaction does not exist
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
    public void getSpecificTransactionThatDoesNotExistWhenUserShouldNotReturnFound() throws Exception {
        mockMvc.perform(get("/transactions/8"))
                .andExpect(jsonPath("$").value(containsString("No transactions found")))
                .andExpect(status().isBadRequest());
    }

    //This user has no access to the transaction
    @Test
    @WithMockUser(username = "UserBank",password = "welkom10", roles = "USER")
    public void getSpecificTransactionWhenUserShouldNotReturnFound() throws Exception {
        mockMvc.perform(get("/transactions/12"))
                .andExpect(jsonPath("$").value(containsString("Transactions does not belong to user")))
                .andExpect(status().isBadRequest());
    }

    //THIS IS FOR WHEN THE TEST CREATES NEW TRANSACTIONS, THE NEW TOTAL IS 9
    @Test
    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
    public void getTransactionsAsEmployeeShouldReturnSizeOf9() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andExpect(jsonPath("$", hasSize(9)))
                .andExpect(jsonPath("$[0].transactionId").value(12))
                .andExpect(jsonPath("$[1].transactionId").value(13))
                .andExpect(jsonPath("$[8].transactionId").value(20));
    }

    //READ ABOVE!! USE THIS WHEN YOU RUN THE CLASS ONE BY ONE
    //This only works when you run it alone, because if you run everything at ones is creates new transaction and expects 9 trans actions
    //This user is an employee, so he has access to all transactions
//    @Test
//    @WithMockUser(username = "EmployeeBank",password = "employee123", roles = "EMPLOYEE")
//    public void getTransactionsAsEmployeeShouldReturnSizeOf5() throws Exception {
//
//        mockMvc.perform(get("/transactions"))
//                .andExpect(jsonPath("$", hasSize(5)))
//                .andExpect(jsonPath("$[0].transactionId").value(12))
//                .andExpect(jsonPath("$[1].transactionId").value(13))
//                .andExpect(jsonPath("$[2].transactionId").value(14))
//                .andExpect(jsonPath("$[3].transactionId").value(15))
//                .andExpect(status().isFound());
//
//
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}