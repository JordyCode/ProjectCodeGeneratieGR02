/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.api.model.Entity.Transaction;
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
    @RequestMapping(value = "/transactions/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Transaction> getSpecificTransaction(@Parameter(in = ParameterIn.PATH, description = "ID from the transaction", required=true, schema=@Schema()) @PathVariable("id") Integer id);


    @Operation(summary = "Gets transactions", description = "Employee can see all transactions, regular user can see only their own transactions", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "users", "employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesfully retrieved transactions!", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),
        
        @ApiResponse(responseCode = "400", description = "BadRequest") })
    @RequestMapping(value = "/transactions",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<Transaction>> transactionsGet(@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return." ,schema=@Schema(allowableValues={  }, minimum="1", maximum="50"
, defaultValue="20")) @Valid @RequestParam(value = "limit", required = false, defaultValue="20") Integer limit);


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
    ResponseEntity<List<Transaction>> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "Create transactions", schema=@Schema()) @Valid @RequestBody Transaction body);

}

