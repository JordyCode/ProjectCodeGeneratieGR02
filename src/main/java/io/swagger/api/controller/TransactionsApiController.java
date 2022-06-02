//package io.swagger.api.controller;
//
//import io.swagger.api.TransactionsApi;
//import io.swagger.api.model.Entity.Transaction;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.annotations.media.Schema;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.validation.constraints.*;
//import javax.validation.Valid;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.List;
//
//@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-04T11:53:18.205Z[GMT]")
//@RestController
//public class TransactionsApiController implements TransactionsApi {
//
//    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);
//
//    private final ObjectMapper objectMapper;
//
//    private final HttpServletRequest request;
//
//    @Autowired
//    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
//        this.objectMapper = objectMapper;
//        this.request = request;
//    }
//
//    public ResponseEntity<Transaction> getSpecificTransaction(@Parameter(in = ParameterIn.PATH, description = "ID from the transaction", required=true, schema=@Schema()) @PathVariable("id") Integer id) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<Transaction>(objectMapper.readValue("{\n  \"amount\" : 200,\n  \"account_to\" : \"NL20INHO0032070023\",\n  \"performed_by\" : 151,\n  \"account_from\" : \"NL20INHO0032076001\",\n  \"type\" : \"Deposit\",\n  \"transactionId\" : 1,\n  \"timestamp\" : \"29/04/2021\"\n}", Transaction.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<Transaction>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<Transaction>(HttpStatus.NOT_IMPLEMENTED);
//    }
//
//    public ResponseEntity<List<Transaction>> transactionsGet(@Min(1) @Max(50) @Parameter(in = ParameterIn.QUERY, description = "The numbers of transactions to return." ,schema=@Schema(allowableValues={  }, minimum="1", maximum="50"
//, defaultValue="20")) @Valid @RequestParam(value = "limit", required = false, defaultValue="20") Integer limit) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("{\n  \"id\" : 666,\n  \"timestamp\" : \"2022-21-04T17:59:44+01:00\",\n  \"account_from\" : \"NL81ABNA0378156792\",\n  \"account_to\" : \"NL81ABNA0367185389\",\n  \"amount\" : 3000\n}", List.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
//    }
//
//    public ResponseEntity<List<Transaction>> transactionsPost(@Parameter(in = ParameterIn.DEFAULT, description = "Create transactions", schema=@Schema()) @Valid @RequestBody Transaction body) {
//        String accept = request.getHeader("Accept");
//        if (accept != null && accept.contains("application/json")) {
//            try {
//                return new ResponseEntity<List<Transaction>>(objectMapper.readValue("[ {\n  \"amount\" : 200,\n  \"account_to\" : \"NL20INHO0032070023\",\n  \"performed_by\" : 151,\n  \"account_from\" : \"NL20INHO0032076001\",\n  \"type\" : \"Deposit\",\n  \"transactionId\" : 1,\n  \"timestamp\" : \"29/04/2021\"\n}, {\n  \"amount\" : 200,\n  \"account_to\" : \"NL20INHO0032070023\",\n  \"performed_by\" : 151,\n  \"account_from\" : \"NL20INHO0032076001\",\n  \"type\" : \"Deposit\",\n  \"transactionId\" : 1,\n  \"timestamp\" : \"29/04/2021\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
//            } catch (IOException e) {
//                log.error("Couldn't serialize response for content type application/json", e);
//                return new ResponseEntity<List<Transaction>>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
//    }
//
//}
