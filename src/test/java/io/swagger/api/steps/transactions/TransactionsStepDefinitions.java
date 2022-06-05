package io.swagger.api.steps.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class TransactionsStepDefinitions extends BaseStepDefinitions implements En {

    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGcmVkZHlVc2VyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTQ0MjIxNDksImV4cCI6MTY1NDQyNTc0OX0.W-WQdfDNv4JaVQzh-cDvD_B-jgXb2ebJzL--g6m6K6U";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDQyMjE2NSwiZXhwIjoxNjU0NDI1NzY1fQ.sYoxH3PyBDx01jVIbgwAM9ND7C07mbpLIr_8Q3PD5Sg";


    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private TransactionDTO dto;
    private Transaction transaction;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String token;
//    private Integer status;
    private HttpEntity<String> request;

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

        Given("^I have an invalid token for employee$", () -> {
            token = INVALID_TOKEN;
        });

        Given("^I have an expired token$", () -> {
            token = EXPIRED_TOKEN;
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

        Given("^I have a valid token for user$", () -> {
        });

        And("^I have a valid transaction object$", () -> {

        });
        When("^I request the /transactions/(\\d+) endpoint$", (Integer arg0) -> {

        });

//        When("^I request the /transactions/id with (\\d+) endpoint$", (Integer id) -> {
//            httpHeaders.add("Authorization", "Bearer " + token);
//            request = new HttpEntity<>(null, httpHeaders);
//            response = restTemplate.exchange(getBaseUrl() + "/transactions/13", HttpMethod.GET, request, String.class);
//        });

        When("^I request the /transactions/id with 13 endpoint$", () -> {
                    httpHeaders.add("Authorization", "Bearer " + token);
                    request = new HttpEntity<>(null, httpHeaders);
                    response = restTemplate.exchange(getBaseUrl() + "/transactions/13", HttpMethod.GET, request, String.class);
                });


//        When("^I request the /transactions/{} endpoint$", () -> {
//            httpHeaders.add("Authorization", "Bearer " + token);
//            request = new HttpEntity<>(null, httpHeaders);
//            response = restTemplate.exchange(getBaseUrl() + "/transactions", HttpMethod.POST,
//                    new HttpEntity<>(null, httpHeaders), String.class);
//        });


//        @Then("^I get a list of (\\d+) transactions$", (Integer arg0) -> {
//        });


//        Given("^I have a valid token for \"([^\"]*)\"$", (String role) -> {
//            switch (role) {
//                case "user" -> token = VALID_USER_TOKEN;
//                case "employee" -> token = VALID_EMPLOYEE_TOKEN;
//                default -> throw new IllegalArgumentException("No such role");
//            }
//        });


    }
}
