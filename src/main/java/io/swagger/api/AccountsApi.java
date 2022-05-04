/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.AccountDTO;
import io.swagger.model.UserDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:28:37.037Z[GMT]")
@Validated
public interface AccountsApi {

    @Operation(summary = "This GET returns accounts", description = "List of all bank accounts if you're an employee. List of your own accounts if you're a regular user", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Account" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "List with all accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "There was no list found, try again later") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<UserDTO>> accountsGet(@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of accounts to return." ,schema=@Schema(allowableValues={  }, minimum="1", maximum="50"
, defaultValue="20")) @Valid @RequestParam(value = "limit", required = false, defaultValue="20") Integer limit);


    @Operation(summary = "Get a specefic account", description = "ID of the bank account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Account" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Return a specefic account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "There was account found with this {ID} ID, try again later") })
    @RequestMapping(value = "/accounts/{ID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<AccountDTO>> accountsIDGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID);


    @Operation(summary = "Update a specefic account", description = "This POST updates a specefic account, connected by it's ID", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Account" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Update a specefic account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct"),
        
        @ApiResponse(responseCode = "404", description = "Something went wrong, try again later") })
    @RequestMapping(value = "/accounts/{ID}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<List<AccountDTO>> accountsIDPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID);


    @Operation(summary = "Create a new account", description = "This will create a new bank account", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "Account" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created a new account", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AccountDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "Input not correct") })
    @RequestMapping(value = "/accounts",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<List<AccountDTO>> accountsPost();

}

