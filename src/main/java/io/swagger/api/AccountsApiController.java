package io.swagger.api;

import io.swagger.model.AccountDTO;
import io.swagger.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:28:37.037Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<UserDTO>> accountsGet(@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of accounts to return." ,schema=@Schema(allowableValues={  }, minimum="1", maximum="50"
, defaultValue="20")) @Valid @RequestParam(value = "limit", required = false, defaultValue="20") Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<UserDTO>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n}, {\n  \"accountStatus\" : \"Active\",\n  \"firstName\" : \"Peter\",\n  \"lastName\" : \"Visser\",\n  \"address\" : {\n    \"country\" : \"The Netherlands\",\n    \"streetName\" : \"Clematislaan\",\n    \"city\" : \"Oegstgeest\",\n    \"postalCode\" : \"2343VK\",\n    \"houseNumber\" : 69\n  },\n  \"phoneNumber\" : \"683726482\",\n  \"role\" : \"Customer\",\n  \"dayLimit\" : 200,\n  \"dateOfBirth\" : \"0013-05-23T00:00:00.000+00:00\",\n  \"accounts\" : {\n    \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n    \"balance\" : 3242.11,\n    \"absoluteLimit\" : -500,\n    \"accountType\" : \"Current\"\n  },\n  \"transactionLimit\" : 2000,\n  \"userId\" : 10,\n  \"email\" : \"Peter_Visser@hotmail.com\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<UserDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<UserDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<AccountDTO>> accountsIDGet(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<AccountDTO>>(objectMapper.readValue("[ {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n}, {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<AccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<AccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<AccountDTO>> accountsIDPut(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("ID") Integer ID) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<AccountDTO>>(objectMapper.readValue("[ {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n}, {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<AccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<AccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<AccountDTO>> accountsPost() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<AccountDTO>>(objectMapper.readValue("[ {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n}, {\n  \"IBAN\" : \"NL91 ABNA 0417 1643 00\",\n  \"balance\" : 3242.11,\n  \"absoluteLimit\" : -500,\n  \"accountType\" : \"Current\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<AccountDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<AccountDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
