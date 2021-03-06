/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.34).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.api.model.DTO.UserDTO;
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
public interface UsersApi {

    @Operation(summary = "Get a specific user", description = "When you are an admin you are able to see other users data. When you are a user you can only see your own details.", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "users" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Getting specific user successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "UserDTO not found") })
    @RequestMapping(value = "/users/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> getSpecificUser(@Parameter(in = ParameterIn.PATH, description = "ID from the user", required=true, schema=@Schema()) @PathVariable("userId") Long userId);


    @Operation(summary = "Gets list of users", description = "Employee can see all users, regular users can only see their own details", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "users", "employees" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Succesfully retrieved users!", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
        
        @ApiResponse(responseCode = "400", description = "BadRequest") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> usersGet();


    @Operation(summary = "Updates an user", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "users" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "user data", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "404", description = "user not found") })
    @RequestMapping(value = "/users/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<?> usersIdPut(@Parameter(in = ParameterIn.PATH, description = "ID of the user to update", required=true, schema=@Schema()) @PathVariable("userId") Long userId,  @Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody User body);


    @Operation(summary = "Creates a new user.", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees", "users" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Created a new user", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter") })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<?> usersPost(@Parameter(in = ParameterIn.DEFAULT, description = "Creates a new user account", required=true, schema=@Schema()) @Valid @RequestBody UserDTO body);

    @Operation(summary = "Gets list of users where list is null", description = "Employee can see all users", security = {
            @SecurityRequirement(name = "bearerAuth")    }, tags={ "employees" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Succesfully retrieved users!", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))),

            @ApiResponse(responseCode = "400", description = "BadRequest") })
    @RequestMapping(value = "/usersWhenNull",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<?> usersGetAccountsIsNull();

}

