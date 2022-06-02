package io.swagger.api.steps.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java8.En;
import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.steps.BaseStepDefinitions;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class AccountsStepDefinitions extends BaseStepDefinitions implements En {

    //Might have to re-assign a token to user and employee
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyQmFuayIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjU0MTg3NjQ5LCJleHAiOjE2NTQxOTEyNDl9.tpmWutCzsvbU-qHaJLMPwkCog3JCL_IKnaTEoglvEg8";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE4NzY2NSwiZXhwIjoxNjU0MTkxMjY1fQ.4__jXHnDOZ5EC_QinpaVBvT_U6jMajuSHqNHHwgBkBc";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private AccountDTO dto;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String token;
    private HttpEntity<String> request;

    public AccountsStepDefinitions() {

        When("^I request /accounts without login token$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(dto), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/accounts", request, String.class);
        });

        Then("^I get a response of (\\d+)$", (Integer status) -> {
            Assertions.assertEquals(status, (Integer) response.getStatusCodeValue());
        });

        When("^I request the accounts endpoint$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts", HttpMethod.GET,
                    new HttpEntity<>(null, httpHeaders), String.class);
        });
    }
}
