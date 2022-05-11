package io.swagger.api.controller;

import io.swagger.api.UsersApi;
import io.swagger.api.model.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<User> getSpecificUser(@Parameter(in = ParameterIn.PATH, description = "ID from the user", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<User>(objectMapper.readValue("{\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n}", User.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<User>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> usersGet(@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of users to return." ,schema=@Schema(allowableValues={  }, minimum="1", maximum="50"
, defaultValue="20")) @Valid @RequestParam(value = "limit", required = false, defaultValue="20") Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n}, {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> usersIdPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n}, {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<UserDTO> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "Creates a new user account", required=true, schema=@Schema()) @Valid @RequestBody User body) {

        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(body, User.class);

        user = userService.add(user);

        UserDTO response = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<UserDTO>(response, HttpStatus.CREATED);
    }

}
