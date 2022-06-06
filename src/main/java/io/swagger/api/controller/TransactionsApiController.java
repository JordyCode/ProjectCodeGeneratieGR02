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
import io.swagger.api.repository.TransactionRepository;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.threeten.bp.OffsetDateTime;
import springfox.documentation.builders.ResponseBuilder;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.*;

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

    @Autowired
    private TransactionRepository transactionRepository;

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
                return ResponseEntity.status(HttpStatus.FOUND).body(transactions);
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

                    return ResponseEntity.status(HttpStatus.FOUND).body(transaction);
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
            Principal principal = request.getUserPrincipal();
            User user = userService.findByUsername(principal.getName());

            Transaction transaction = new Transaction();
            Account account = accountRepository.findByIBAN(body.getAccountFrom());
            transaction.setAccountFrom(body.getAccountFrom());
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.TRANSACTION);
            transaction.setTimestamp(OffsetDateTime.now().toString());;
            transaction.setPerformedBy(user.getUserId().intValue());
            transaction.setUser(account.getUser());
            transaction.setAmount(body.getAmount());
            Transaction created = transactionService.addTransaction(transaction);
            Account sender = accountRepository.findByIBAN(transaction.getAccountFrom());

            Long total = transactionRepository.getTransactionsTotalByUser(sender.getUser().getUserId());
            if (total == null){
                total = 0L;
            }
            Double dailyLimitLeft = sender.getUser().getDayLimit() - total.doubleValue();

            if (Objects.equals(created.getPerformedBy(), sender.getUser().getUserId().intValue())) {
                Map<String, Object> result = new HashMap<String,Object>();
                result.put("Transaction", created);
                result.put("Total amount left of daily limit",dailyLimitLeft.doubleValue());
                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.CREATED);

                //return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(result + dailyLimitLeft.toString() + " is left of your daily limit!");
            } else {

                return ResponseEntity.status(HttpStatus.CREATED).body(created);
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> postTransactionDeposit(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody DepositTransactionDTO body) {
        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.findByUsername(principal.getName());
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(bankIban);
            transaction.setAccountTo(body.getAccountTo());
            transaction.setType(Transaction.TypeEnum.DEPOSIT);
            transaction.setTimestamp(OffsetDateTime.now().toString());
            transaction.setUser(user);
            transaction.setPerformedBy(user.getUserId().intValue());
            transaction.setAmount(body.getAmount());
            Transaction result = transactionService.addDepositTransaction(transaction);
            Account account = accountRepository.findByIBAN(body.getAccountTo());

            Map<String, Object> result2 = new HashMap<String,Object>();
            result2.put("Transaction", result);
            result2.put("Amount deposited:",transaction.getAmount().doubleValue());
            result2.put("New balance:", account.getBalance());
            return new ResponseEntity<Map<String, Object>>(result2, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    public ResponseEntity<?> postTransactionWithdraw(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody WithdrawTransactionDTO body) {
        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.findByUsername(principal.getName());
            Transaction transaction = new Transaction();
            transaction.setAccountFrom(body.getAccountFrom());
            transaction.setAccountTo(bankIban);
            transaction.setType(Transaction.TypeEnum.WITHDRAW);
            transaction.setUser(user);
            transaction.setTimestamp(OffsetDateTime.now().toString());
            transaction.setPerformedBy(user.getUserId().intValue());
            transaction.setAmount(body.getAmount());
            Transaction result = transactionService.addWithdrawTransaction(transaction);
            Account account = accountRepository.findByIBAN(body.getAccountFrom());
            Map<String, Object> result3 = new HashMap<String,Object>();
            result3.put("Transaction", result);
            result3.put("Amount withdrew:",transaction.getAmount().doubleValue());
            result3.put("New balance:", account.getBalance());
            return new ResponseEntity<Map<String, Object>>(result3, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}