package io.swagger.api.steps.users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.model.Entity.User;
import io.swagger.api.service.UserService;
import io.swagger.api.steps.BaseStepDefinitions;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class UsersStepDefinitions extends BaseStepDefinitions implements En {

    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_EMPTY_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXB0eVVzZXIiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDQyNDg5MiwiZXhwIjoxNjU0NDI4NDkyfQ.J_uGkdHtgH72mBI11SbrLk15G0A87OKmaxbdmTpUpcU";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGcmVkZHlVc2VyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTQ0MjIxNDksImV4cCI6MTY1NDQyNTc0OX0.W-WQdfDNv4JaVQzh-cDvD_B-jgXb2ebJzL--g6m6K6U";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDQyMjE2NSwiZXhwIjoxNjU0NDI1NzY1fQ.sYoxH3PyBDx01jVIbgwAM9ND7C07mbpLIr_8Q3PD5Sg";


    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private User user;
    private UserDTO userDTO;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private String token;
    private Integer status;
    private HttpEntity<String> request;
    private UserService userService;


    public UsersStepDefinitions() {

        Given("^I have a expired token for users$", () -> {
            token = EXPIRED_TOKEN;
        });

        Given("^I have a valid but empty token for users", () -> {
            token = VALID_EMPTY_USER;
        });

        Given("^I have a invalid token for users$", () -> {
            token = INVALID_TOKEN;
        });

        Given("^I have a valid user token for users$", () -> {
            token = VALID_USER_TOKEN;
        });

        Given("^I have a valid employee token for users$", () -> {
            token = VALID_EMPLOYEE_TOKEN;
        });

        Then("^I get a response of (\\d+) for users$", (Integer status) -> {
            Assertions.assertEquals(status, (Integer) response.getStatusCodeValue());
        });
        When("^I request the /users endpoint$", () -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/users", HttpMethod.GET,
                    new HttpEntity<>(null, httpHeaders), String.class);
        });

        Then("^I get a list of (\\d+) users$", (Integer size) -> {
            Integer actual = JsonPath.read(response.getBody(), "$.size()");
            Assertions.assertEquals(size, actual);
        });

        When("^I request /users without login token$", () -> {
            httpHeaders.add("Content-Type", "application/json");

            request = new HttpEntity<String>(mapper.writeValueAsString(user), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/users", request, String.class);
        });

        When("^I request the /users/userId with (\\d+) endpoint$", (Integer arg0) -> {
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(null, httpHeaders);
            response = restTemplate.exchange(getBaseUrl() + "/users/5", HttpMethod.GET, request, String.class);
        });

//        When("^I request the POST /users endpoint$$", () -> {
//            httpHeaders.add("Authorization", "Bearer " + token);
//            request = new HttpEntity<>(mapper.writeValueAsString(user), httpHeaders);
//            response = restTemplate.postForEntity(getBaseUrl() + "/users", request, String.class);
//            status = response.getStatusCodeValue();
//        });

        When("^I request the POST /users endpoint", () -> {

            user = new User();
            user.setUsername("CucumberUser");
            user.setPassword("Welkom10");
            user.setFirstName("Cucumber");
            user.setLastName("User");
            user.setAccountStatus(User.AccountStatusEnum.ACTIVE);
            user.setDayLimit(1000.00);
            user.setTransactionLimit(1000.00);
            user.setEmail("cucumber@test.nl");
            user.setDateOfBirth("01/01/200");
            userService.add(user, false);

            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(mapper.writeValueAsString(user), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/users", request, String.class);
            status = response.getStatusCodeValue();

            ObjectMapper mapper = new ObjectMapper();

//            HttpEntity<String> httpEntity = new HttpEntity<>(mapper.writeValueAsString(user), request);

        });

}
    }
