package io.swagger.api.steps.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.steps.BaseStepDefinitions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

public class AccountsStepDefinitions extends BaseStepDefinitions implements En {

    //Might have to re-assign a token to user and employee
    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyQmFuayIsImF1dGgiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiaWF0IjoxNjU0MzUwMjY1LCJleHAiOjE2NTQzNTM4NjV9.qpf4MG6i6TiDcAGKbdi8eU9550QD0JxEmEqkIHD3Y5c";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDM1MTY1NywiZXhwIjoxNjU0MzU1MjU3fQ.bojjBjzKRm5RSke1yKMNF7ailJkg3MQwjnkGvuBt2Mk";

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private AccountDTO accountDTO;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String token;
    private HttpEntity<String> request;

    public AccountsStepDefinitions() {

        When("^I request /accounts without login token$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(accountDTO), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/accounts", request, String.class);
        });

        When("^I request the /accounts endpoint$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts", HttpMethod.GET, request, String.class);
        });

        When("^I request /accounts/id with id of 7 without login token$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(accountDTO), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/accounts/7", request, String.class);
        });

        When("^I request /accounts/id with id of 1$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/1", HttpMethod.GET, request, String.class);
        });

        When("^I request /accounts/id with id of 7$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/7", HttpMethod.GET, request, String.class);
        });

        When("^I make a PUT request on the /accounts/id endpoint$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(accountDTO), httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/accounts/id", HttpMethod.PUT, request, String.class);
        });

        When("^I make a PUT request on the /accounts/id with id of (\\d+)$", (Integer id) -> {
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("absoluteLimit", 100.0);

            HttpEntity<String> entity = new HttpEntity<>(jsonObject.getString("absoluteLimit"), httpHeaders);

            response = restTemplate.exchange(getBaseUrl() + "/accounts/" + id, HttpMethod.PUT, entity, String.class);
        });

        Given("^I have an \"([^\"]*)\" bearer token", (String tokenType) -> {
            switch (tokenType) {
                case "invalid":
                    token = INVALID_TOKEN;
                    break;
                case "expired":
                    token = EXPIRED_TOKEN;
                    break;
                case "user":
                    token = VALID_USER_TOKEN;
                    break;
                case "employee":
                    token = VALID_EMPLOYEE_TOKEN;
                    break;
                default:
                    throw new IllegalArgumentException("No type to get a token");
            }
        });

        Then("^I get a response of (\\d+)$", (Integer status) -> {
            Assertions.assertEquals(status, (Integer) response.getStatusCodeValue());
        });

        Then("^I get a list of (\\d+) accounts$", (Integer listSize) -> {
            Integer actualListSize = JsonPath.read(response.getBody(), "$.size()");
            Assertions.assertEquals(actualListSize, listSize);
        });

        And("^I get JSON objects containing \"([^\"]*)\" with value of \"([^\"]*)\"$", (String ibanName, String expectedIban) -> {
            JSONObject jsonObject = new JSONObject(response.getBody());

            Assertions.assertEquals(expectedIban, jsonObject.getString(ibanName));
        });

        And("^I get JSON array containing \"([^\"]*)\" with value of \"([^\"]*)\"$", (String ibanName, String expectedArray) -> {
            JSONArray jsonArray = new JSONArray(response.getBody());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ibanName = jsonObject.getString("iban");
            }

            Assertions.assertEquals(expectedArray, ibanName);
        });
    }
}
