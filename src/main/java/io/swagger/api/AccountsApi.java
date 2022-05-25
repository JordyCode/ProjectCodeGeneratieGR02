/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "This GET returns accounts", description = "List of all bank accounts if you're an employee. List of your own accounts if you're a regular user", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "AccountDTO" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "List with all accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "There was no list found, try again later") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> accountsGet();


    @Operation(summary = "Get a specific account", description = "ID of the bank account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "AccountDTO" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return a specific account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "There was account found with this {ID} ID, try again later") })
    @RequestMapping(value = "/accounts/{ID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> accountsIDGet(@Parameter(in = ParameterIn.PATH, description = "Account ID", required=true, schema=@Schema()) @PathVariable("ID") Long ID);


    @Operation(summary = "Update a specific account", description = "This POST updates a specific account, connected by it's ID", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "AccountDTO" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Update a specific account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "Something went wrong, try again later") })
    @RequestMapping(value = "/accounts/{ID}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<?> accountsIDPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Long ID, @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody Account body);


    @Operation(summary = "Create a new account", description = "This will create a new bank account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "AccountDTO" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created a new account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<?> accountsPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody AccountDTO body);

}

