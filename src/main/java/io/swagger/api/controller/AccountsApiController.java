package io.swagger.api.controller;

import io.swagger.api.AccountsApi;
import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.DTO.BalanceDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Role;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@RestController
@RequestMapping(value = "/accounts")
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private AccountService accountService;

    private UserService userService;

    @Value("${bank.iban}")
    private String bankIban;

    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','USER')")
    @GetMapping
    public ResponseEntity<?> accountsGet() {
        try {
            // Create a list to save the accounts
            List<Account> accounts = new ArrayList<>();

            // Check the role of the user
            if (request.isUserInRole("ROLE_EMPLOYEE")) {
                // When the user is an employee it will get all the accounts
                accounts = accountService.getAllAccounts();
            } else {
                // Get the security information of the user
                Principal userSecurityInfo = request.getUserPrincipal();

                // Get the current user
                User user = userService.findByUsername(userSecurityInfo.getName());

                accounts = accountService.getAccountsByUser(user);
            }

            if (accounts != null) {
                return ResponseEntity.status(HttpStatus.OK).body(accounts);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("accounts/{id}")
    public ResponseEntity<?> accountsIDGet(@Parameter(in = ParameterIn.PATH, description = "Account id", required=true, schema=@Schema()) @PathVariable("id") Long id) {
        try
        {
            // Get the security information of the user
            Principal userSecurityInfo = request.getUserPrincipal();
            User user = userService.findByUsername(userSecurityInfo.getName());

            // Check if the user is an employee or a normal user and check if the account user is equal to the id of the user
            if (request.isUserInRole("ROLE_EMPLOYEE") || accountService.checkIfAccountIsUser(id, user)) {
                Account account = accountService.getAccountById(id);
                if (account.getId() == 1) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
                if (account != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(account);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> accountsIDPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Long id, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Account body) {
        try {
            if (id == 1) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            } else {
                body.setId(id);
            }

            Account result = accountService.save(body);

            return ResponseEntity.status(200).body(result);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> accountsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody AccountDTO body) {
        try {
            // Create a new account
            Account account = new Account();

            // Set the properties
            account.setAccountType(body.getAccountTypeEnum());
            account.setUser(body.getUser());
            account.setAbsoluteLimit(body.getAbsoluteLimit());
            account.setAccountStatus(body.getAccountStatusEnum());
            account.setBalance(body.getBalance());

            // Add the new properties to the Account result
            Account result = accountService.add(account, true);

            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','USER')")
    public ResponseEntity<?> accountsGetTotalBalance() {
        try {
            // create total balance variable
            Double totalBalance = 0.00;
            // Create a list to save the accounts
            List<Account> accounts = new ArrayList<>();

            // Get the security information of the user
            Principal userSecurityInfo = request.getUserPrincipal();

            // Get the current user
            User user = userService.findByUsername(userSecurityInfo.getName());

            // Fill list with all the accounts of the user
            accounts = accountService.getAccountsByUser(user);

            // Create BalanceDTO
            BalanceDTO balanceDTO = new BalanceDTO();

            if (accounts.size() > 0) {
                for (Account a : accounts) {
                    totalBalance += a.getBalance();
                }
                balanceDTO.setTotalBalance(totalBalance);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (balanceDTO != null) {
                return ResponseEntity.status(HttpStatus.OK).body(balanceDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
