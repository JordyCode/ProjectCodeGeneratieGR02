package io.swagger.api.steps.users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java8.En;
import io.cucumber.java8.Status;
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

import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class UsersStepDefinitions extends BaseStepDefinitions implements En {

    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NDE2NTM2MCwiZXhwIjoxNjU0MTY4OTYwfQ.j8dj-6HtG9uA0Oo---iJhUfNyHQh_GQWlk8a_AO-H-Y";
    private static final String INVALID_TOKEN = "this-token-doesnt-work";
    private static final String VALID_USER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJGcmVkZHlVc2VyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTU4ODE3ODAsImV4cCI6MTY1NjI0MTc4MH0.62Rk6NU9Yec0fMsQzdGq5m6pPztAQhqAFDX_4fekJtk";
    private static final String VALID_EMPLOYEE_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbXBsb3llZUJhbmsiLCJhdXRoIjpbeyJhdXRob3JpdHkiOiJST0xFX0VNUExPWUVFIn0seyJhdXRob3JpdHkiOiJST0xFX1VTRVIifV0sImlhdCI6MTY1NTg4MTYwOSwiZXhwIjoxNjU2MjQxNjA5fQ.Krna1Y2msJ1u6Ql9mLsJI_16pNt1VS-Lgs9P6tjLqso";
    private static final String VALID_EMPTY_USER = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbXB0eVVzZXIyIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE2NTU4ODE3MzgsImV4cCI6MTY1NjI0MTczOH0.eYQgeOjj05o9DeZBXjnz2aCUan9yz0uGhakbZmG8-qE";


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
    private Map<String, Serializable> updateData;


    public UsersStepDefinitions() {

        Given("^I have a expired token for users$", () -> {
            token = EXPIRED_TOKEN;
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

        Given("^I have a valid user token for empty users$", () -> {
            token = VALID_EMPTY_USER;
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

        When("^I request the POST /users endpoint", () -> {

            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);
            request = new HttpEntity<>(mapper.writeValueAsString(user), httpHeaders);
            response = restTemplate.postForEntity(getBaseUrl() + "/users", request, String.class);
            status = response.getStatusCodeValue();

            ObjectMapper mapper = new ObjectMapper();
        });

        And("^I have all user objects filled$", () -> {
            user = new User();
            user.setUsername("CucumberUser");
            user.setPassword("Welkom10");
            user.setFirstName("Cucumber");
            user.setLastName("User");
            user.setUserStatus(User.UserStatusEnum.ACTIVE);
            user.setDayLimit(1000.00);
            user.setTransactionLimit(500.00);
            user.setEmail("Cuccumber@mail.ml");;
            user.setDateOfBirth("01/01/2000");

        });

        And("^I have user objects that need to be updated$", () -> {

            updateData = new HashMap<>();

            updateData.put("userId" , "5");
            updateData.put("dayLimit", "50.0");
            updateData.put("username" , "UserBank");
            updateData.put("password" , "user123");
            updateData.put("firstName" , "Frank");
            updateData.put("lastName" , "Dersjant");
            updateData.put("userStatus" , "Active");
            updateData.put("transactionLimit", "200.0");
            updateData.put("email" , "frank.dersjant@test.com");
            updateData.put( "dateOfBirth", "01/01/1970");

        });

        When("^I request the PUT /users/id with id of (\\d+) endpoint$", (Integer arg0) -> {
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Authorization", "Bearer " + token);

            request = new HttpEntity<>(mapper.writeValueAsString(updateData), httpHeaders);

            response = restTemplate.exchange(getBaseUrl() + "/users/" + arg0, HttpMethod.PUT ,request, String.class);
            status = response.getStatusCodeValue();
        });

    }
}
