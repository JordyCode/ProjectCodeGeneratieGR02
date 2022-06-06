package io.swagger.api.steps.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import io.swagger.api.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class TransactionsStepDefinitions extends BaseStepDefinitions implements En {

    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGcmVkZHlVc2VyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTQ1MDE0OTksImV4cCI6MTY1NDUwNTA5OX0.6Bb-6ttp8acguKIS6XepOWsIZacmBPUvKevmh4m6GSM";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDUwMTU1NSwiZXhwIjoxNjU0NTA1MTU1fQ.t4fTJRYvgDZwHg6bH13dv9bcfsIiwkj2XJXCDkz-Wrc";
    private static final String VALID_EMPTY_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXB0eVVzZXIiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDUwMTUyOCwiZXhwIjoxNjU0NTA1MTI4fQ.WQZjrO5NzsGMWyu7MJD1f4gL7ls-WiyVSFpp5s8EPjc";


    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private TransactionDTO transactionDTO;
    private DepositTransactionDTO depositDTO;
    private WithdrawTransactionDTO withdrawDTO;
    private AccountDTO accountDTO1;
    private AccountDTO accountDTO2;
    private Transaction transaction;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String token;
    private Integer status;
    private HttpEntity<String> request;
    private String bankIban = "NL01INHO0000000001";


    public TransactionsStepDefinitions() {

        When("^I request /transactions without login token$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(transaction), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/transactions", request, String.class);
        });

        Then("^I get a response of (\\d+) for transactions$", (Integer status) -> {
            Assertions.assertEquals(status, (Integer) response.getStatusCodeValue());
        });

        Given("^I have a valid token for employee$", () -> {
            token = VALID_EMPLOYEE_TOKEN;
        });

        Given("^I have a valid token for empty user", () -> {
            token = VALID_EMPTY_USER;
        });

        Given("^I have an invalid token", () -> {
            token = INVALID_TOKEN;
        });

        Given("^I have an expired token$", () -> {
            token = EXPIRED_TOKEN;
        });

        Given("^I have a valid token for user", () -> {
            token = VALID_USER_TOKEN;
        });

        When("^I request the /transactions endpoint$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/transactions", HttpMethod.GET,
                    new HttpEntity<>(null, httpHeaders), String.class);
        });

        Then("^I get a list of (\\d+) transactions$", (Integer size) -> {
            Integer actual = JsonPath.read(response.getBody(), "$.size()");
            Assertions.assertEquals(size, actual);

        });

        Then("^I get a specific transaction of transactions/(\\d+)", (Integer id) -> {
            Assertions.assertEquals(id, (transaction.getTransactionId()));
        });

        And("^I have a valid transaction object for$", () -> {

            transactionDTO = new TransactionDTO();
            User user1 = new User();
            user1.setUsername("CucumberUser1");
            user1.setPassword("Welkom10");
            user1.setFirstName("Cucumber");
            user1.setLastName("User");
            user1.setAccountStatus(User.AccountStatusEnum.ACTIVE);
            user1.setDayLimit(1000.00);
            user1.setTransactionLimit(500.00);
            user1.setEmail("Cuccumber@mail.ml");;
            user1.setDateOfBirth("01/01/2000");
            user1.setUserId(20L);

            User user2 = new User();
            user2.setUsername("CucumberUser2");
            user2.setPassword("Welkom10");
            user2.setFirstName("Cucumber");
            user2.setLastName("User");
            user2.setAccountStatus(User.AccountStatusEnum.ACTIVE);
            user2.setDayLimit(1000.00);
            user2.setTransactionLimit(500.00);
            user2.setEmail("Cuccumber@mail.ml");;
            user2.setDateOfBirth("01/01/2000");
            user2.setUserId(21L);

            accountDTO1 = new AccountDTO();
            accountDTO1.setUser(user1);
            accountDTO1.setIBAN("NL00INHO0000000003");
            accountDTO1.setAccountTypeEnum(Account.AccountTypeEnum.CURRENT);
            accountDTO1.setBalance(1000.00);
            accountDTO1.setAbsoluteLimit(-500.00);

            accountDTO2 = new AccountDTO();
            accountDTO2.setUser(user2);
            accountDTO2.setIBAN("NL00INHO0000000004");
            accountDTO2.setAccountTypeEnum(Account.AccountTypeEnum.CURRENT);
            accountDTO2.setBalance(1000.00);
            accountDTO2.setAbsoluteLimit(-500.00);

            transaction = new Transaction();
            transaction.setAccountTo(accountDTO1.getIBAN());
            transaction.setAccountFrom(accountDTO2.getIBAN());
            transaction.performedBy(user1.getUserId().intValue());
            transaction.timestamp(LocalDateTime.now().toString());
            transaction.type(Transaction.TypeEnum.TRANSACTION);
            transaction.setAmount(50.00);
            transaction.setUser(user1);

        });

        When("^I request the /transactions/(\\d+) endpoint$", (Integer arg0) -> {

        });

        When("^I request the /transactions/id with 13 endpoint$", () -> {
                    httpHeaders.add("Authorization", "Bearer " + token);
                    request = new HttpEntity<>(null, httpHeaders);
                    response = restTemplate.exchange(getBaseUrl() + "/transactions/13", HttpMethod.GET, request, String.class);
                });

        When("^I request the /transactions/id with id of 12$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/transactions/12", HttpMethod.GET, request, String.class);
        });

        When("^I request POST /transactions endpoint$", () -> {
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);

            request = new HttpEntity<>(mapper.writeValueAsString(transactionDTO), httpHeaders);

            response = restTemplate.postForEntity(getBaseUrl() + "/transactions", request, String.class);
            status = response.getStatusCodeValue();
        });

        When("^I request POST /transactions/deposit endpoint$", () -> {
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);

            request = new HttpEntity<>(mapper.writeValueAsString(depositDTO), httpHeaders);

            response = restTemplate.postForEntity(getBaseUrl() + "/transactions/deposit", request, String.class);
            status = response.getStatusCodeValue();
        });

        And("^I have a valid transaction object$", () -> {

            transactionDTO = new TransactionDTO();
            transactionDTO.setAccountFrom("NL53INHO4715545128");
            transactionDTO.setAccountTo("NL53INHO4715545127");
            transactionDTO.setAmount(20.00);
        });

        And("^I have a valid deposit object$", () -> {

            depositDTO = new DepositTransactionDTO();
            depositDTO.setAccountTo("NL53INHO4715545128");
            depositDTO.setAmount(20.00);
        });

        And("^I have a valid withdraw object$", () -> {

            withdrawDTO = new WithdrawTransactionDTO();
            withdrawDTO.setAccountFrom("NL53INHO4715545128");
            withdrawDTO.setAmount(20.00);
        });

        When("^I request POST /transactions/withdraw endpoint$", () -> {
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);

            request = new HttpEntity<>(mapper.writeValueAsString(withdrawDTO), httpHeaders);

            response = restTemplate.postForEntity(getBaseUrl() + "/transactions/withdraw", request, String.class);
            status = response.getStatusCodeValue();
        });

    }
}
