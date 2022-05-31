package io.swagger.api.controller;

import io.swagger.api.UsersApi;
import io.swagger.api.model.DTO.UserDetailsDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.model.Role;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasAnyRole('USER','EMPLOYEE')")
    public ResponseEntity<?> getSpecificUser(@Parameter(in = ParameterIn.PATH, description = "ID from the user", required=true, schema=@Schema()) @PathVariable("userId") Long userId) {
        try {
            // Get the information of the user
            Principal userSecurityInfo = request.getUserPrincipal();
            User user = userService.findByUsername(userSecurityInfo.getName());


            // Check if userId is the same as the current user
            if (user.getUserId() == userId) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                if (request.isUserInRole("ROLE_EMPLOYEE")) {

                    // Call service layer for function getSpecificUser
                    User getSpecificUser = userService.getSpecificUser(userId);

                    // Check if user exist
                    if (user != null) {
                        return ResponseEntity.status(HttpStatus.OK).body(getSpecificUser);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                } else {

                    // Check of accountList.get(1) de IBAN geeft of dat het get(0) is of dat het uberhaupt werkt.
                    List<Account> accountList = user.getAccounts();

                    UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
                    userDetailsDTO.setFirstname(user.getFirstName());
                    userDetailsDTO.setLastname(user.getLastName());
                    userDetailsDTO.setIBAN(String.valueOf(accountList.get(1)));

                    // Check if user exist
                    if (user != null) {
                        return ResponseEntity.status(HttpStatus.OK).body(userDetailsDTO);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                }
            }
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('USER','EMPLOYEE')")
    public ResponseEntity<?> usersGet() {
        try {
            // Create a list for users
            List<User> userList = new ArrayList<>();

            if (request.isUserInRole("ROLE_EMPLOYEE")) {
                // If the user is an Employee then you can call the getAllUsers function
                userList = userService.getAllUsers();
            } else {
                // If the user is an user then you have to add the user to the list
                // Request the information of the user
                Principal userSecurityInfo = request.getUserPrincipal();

                // Get the current user
                User user = userService.findByUsername(userSecurityInfo.getName());

                // Add user to the userList
                userList.add(user);
            }

            // Check if user exist
            if (userList != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userList);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> usersIdPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("userId") Long userId,  @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body) {

        try {
            if (userId == 1) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                body.setUserId(userId);
            }

            if (request.isUserInRole("ROLE_EMPLOYEE")) {
                body.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE));
            } else {
                body.setRoles(Arrays.asList(Role.ROLE_USER));
            }
            User result = userService.saveUser(body);

            return ResponseEntity.status(200).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "Creates a new user account", required=true, schema=@Schema()) @Valid @RequestBody UserDTO body) {

        try {
            User user = new User();
            user.setUsername(body.getUsername());
            user.setPassword(body.getPassword());
            user.setFirstName(body.getFirstName());
            user.setLastName(body.getLastName());
            user.setAccountStatus(User.AccountStatusEnum.ACTIVE);
            user.setDayLimit(body.getDayLimit());
            user.setTransactionLimit(body.getTransactionLimit());
            user.setEmail(body.getEmail());
            user.dateOfBirth(body.getDateOfBirth());

            User result = userService.add(user, false);

            return ResponseEntity.status(200).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
