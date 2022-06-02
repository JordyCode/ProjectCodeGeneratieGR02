//package io.swagger.api.service;
//
//import io.swagger.api.controller.LoginApiController;
//import io.swagger.api.model.Entity.Transaction;
//import io.swagger.api.repository.AccountRepository;
//import io.swagger.api.repository.TransactionRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TransactionService {
//
//    private TransactionRepository transactionRepository;
//
//
//    public TransactionService(TransactionRepository transactionRepository){
//        this.transactionRepository = transactionRepository;
//    }
//
//    public List<Transaction> getAllTransactions(){
//        return transactionRepository.findAll();
//    }
//
//
//}
