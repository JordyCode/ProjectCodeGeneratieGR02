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
//            Account from = accountRepository.findByIBAN(transaction.getAccountFrom());
//            Account to = accountRepository.findByIBAN(transaction.getAccountFrom());
            // Set the properties
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
//
//        try {
//            Principal principal = request.getUserPrincipal();
//            User user = userService.findByUsername(principal.getName());
//            //System.out.println(user.toString());
//
//            Transaction transaction = new Transaction();
//
//            //check if the user owns an account by the given IBAN, if not, check if the user is an employee
//            for (Account account : user.getAccounts()) {
//                if ((account.getIBAN().equals(body.getAccountFrom())) || (user.getRoles().contains(Role.ROLE_EMPLOYEE))){
//                    transaction.setType(Transaction.TypeEnum.TRANSACTION);
//                    transaction.setTimestamp(OffsetDateTime.now().toString());
//                    transaction.setAccountFrom(body.getAccountFrom());
//                    transaction.setAccountTo(body.getAccountTo());
//                    transaction.setPerformedBy(body.getPerformedBy());
//                    transaction.setAmount(body.getAmount());
//                    transaction = transactionService.addTransaction(transaction);
//                    break;
//                   } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//            }
//            return new ResponseEntity<Transaction>(HttpStatus.OK).ok().body(transaction);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    public ResponseEntity<?> postTransactionsTEST(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema = @Schema()) @Valid @RequestBody TransactionDTO body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(body.getAccountFrom());
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.TRANSACTION);
            transaction.setTimestamp(OffsetDateTime.now().toString());;
            transaction.setPerformedBy(body.getPerformedBy());
            transaction.setAmount(body.getAmount());

            Transaction result = transactionService.addTransactionTEST(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> postTransactionDeposit(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created (a specified Id, Timestamp or AccountFrom are not allowed!).", required=true, schema=@Schema()) @Valid @RequestBody DepositTransactionDTO body) {
        try {
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(body.getAccountTo());
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.DEPOSIT);
            transaction.setTimestamp(OffsetDateTime.now().toString());

            transaction.setAmount(body.getAmount());

            Transaction result = transactionService.addDeposit(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<TransactionDTO> postTransactionWithdraw(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created (a specified Id, Timestamp or AccountTo are not allowed!).", required=true, schema=@Schema()) @Valid @RequestBody WithdrawTransactionDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TransactionDTO>(objectMapper.readValue("{\n  \"amount\" : 200,\n  \"account_to\" : \"NL20INHO0032070023\",\n  \"performed_by\" : 151,\n  \"account_from\" : \"NL20INHO0032076001\",\n  \"type\" : \"Deposit\",\n  \"transactionId\" : 1,\n  \"timestamp\" : \"29/04/2021\"\n}", TransactionDTO.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TransactionDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<TransactionDTO>(HttpStatus.NOT_IMPLEMENTED);
    }

}
