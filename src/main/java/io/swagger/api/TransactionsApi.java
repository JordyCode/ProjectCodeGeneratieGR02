/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.api.model.DTO.DepositTransactionDTO;
import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.DTO.WithdrawTransactionDTO;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@Validated
public interface TransactionsApi {

    @Operation(summary = "Get a specific transaction", description = "When you are an admin you are able to see all transactions. When you are a user you can only see your own transactions.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "users", "employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Getting transaction successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))),

            @ApiResponse(responseCode = "400", description = "bad input parameter"),

            @ApiResponse(responseCode = "404", description = "TransactionDTO not found") })
    @RequestMapping(value = "/transactions/{transactionId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> getSpecificTransaction(@Parameter(in = ParameterIn.PATH, description = "The transaction ID", required=true, schema=@Schema()) @PathVariable("transactionId") Integer transactionId);


    @Operation(summary = "Gets transactions", description = "Employee can see all transactions, regular user can see only their own transactions", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "users", "employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully retrieved transactions!", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),

            @ApiResponse(responseCode = "400", description = "BadRequest") })
    @RequestMapping(value = "/transactions",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> transactionsGet();


    @Operation(summary = "Create a new transaction", description = "", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "users", "employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created a new transaction", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),

            @ApiResponse(responseCode = "400", description = "bad input parameter"),

            @ApiResponse(responseCode = "409", description = "Conflict with given amount of money. Exceeds balance.") })
    @RequestMapping(value = "/transactions",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> postTransactions(@Parameter(in = ParameterIn.DEFAULT, description = "", required=true, schema=@Schema()) @Valid @RequestBody TransactionDTO body);

    @Operation(summary = "Create a new depository transaction.", description = "Creates a deposit type (of) transaction that/which allows a user to add the given amount to the balance of their specified bank account.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "transactions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A JSON object of the newly created transaction.", content = @Content(schema = @Schema(implementation = Transaction.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error message response (check request body or parameters).") })
    @RequestMapping(value = "/transactions/deposit",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> postTransactionDeposit(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody DepositTransactionDTO body);


    @Operation(summary = "Create a new withdrawal transaction.", description = "Creates a withdraw type transaction which allows a user to subtract the given amount from the balance of their specified bank account.", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "transactions" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A JSON object of the newly created transaction.", content = @Content(schema = @Schema(implementation = Transaction.class))),

            @ApiResponse(responseCode = "500", description = "Internal server error message response (check request body or parameters).") })
    @RequestMapping(value = "/transactions/withdraw",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<?> postTransactionWithdraw(@Parameter(in = ParameterIn.DEFAULT, description = "JSON data for the transaction that has to be created.", required=true, schema=@Schema()) @Valid @RequestBody WithdrawTransactionDTO body);

}