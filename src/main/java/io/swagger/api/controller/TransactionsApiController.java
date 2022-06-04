package io.swagger.api.controller;

import io.swagger.api.TransactionsApi;
import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransactionService;
import io.swagger.api.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.threeten.bp.OffsetDateTime;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Value("${bank.iban}")
    private String bankIban;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','USER')")
    public ResponseEntity<?> transactionsGet() {
        try {
            // Create a empty list for transactions
            List<Transaction> transactions = new ArrayList<>();

            // Check the role of the user
            if (request.isUserInRole("ROLE_EMPLOYEE")) {
                // User is an employee, getting all transactions
                transactions = transactionService.getAllTransactions();
            } else {
                // Get the security information
                Principal principal = request.getUserPrincipal();

                // Get the current user
                User user = userService.findByUsername(principal.getName());

                // Get the user transactions
                transactions = transactionService.getAllUserTransactions(user);
            }

            if (transactions != null) {
                return ResponseEntity.status(HttpStatus.OK).body(transactions);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE','USER')")
    public ResponseEntity<?> getSpecificTransaction(@Parameter(in = ParameterIn.PATH, description = "The transaction ID", required = true, schema = @Schema()) @PathVariable("transactionId") Integer transactionId) {
        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.findByUsername(principal.getName());
            Transaction transaction = transactionService.getTransactionsById(transactionId);

            if (transaction != null) {
                if (request.isUserInRole("ROLE_EMPLOYEE") || transactionService.checkIfTransactionBelongsToUser(user, transactionId)) {

                    return ResponseEntity.status(HttpStatus.OK).body(transaction);
                } else {

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'USER')")
    public ResponseEntity<?> postTransactions(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema = @Schema()) @Valid @RequestBody TransactionDTO body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(body.getAccountFrom());
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.TRANSACTION);
            transaction.setTimestamp(OffsetDateTime.now().toString());;
            transaction.setPerformedBy(body.getPerformedBy());
            transaction.setAmount(body.getAmount());

            Transaction result = transactionService.addTransaction(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> postTransactionDeposit(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody DepositTransactionDTO body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(bankIban);
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.DEPOSIT);
            transaction.setTimestamp(OffsetDateTime.now().toString());
            transaction.setPerformedBy(body.getPerformedBy());
            transaction.setAmount(body.getAmount());

            Transaction result = transactionService.addDepositTransaction(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> postTransactionWithdraw(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody WithdrawTransactionDTO body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(body.getAccountFrom());
            transaction.setAccountTo(bankIban);
            transaction.setType(Transaction.TypeEnum.WITHDRAW);
            transaction.setTimestamp(OffsetDateTime.now().toString());
            transaction.setPerformedBy(body.getPerformedBy());
            transaction.setAmount(body.getAmount());

            Transaction result = transactionService.addWithdrawTransaction(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}