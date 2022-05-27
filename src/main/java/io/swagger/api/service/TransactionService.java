package io.swagger.api.service;

import io.swagger.api.controller.LoginApiController;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Value("${bank.iban}")
    private String bankIban;

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;

    private LoginApiController loginApiController;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, LoginApiController loginApiController) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.loginApiController = loginApiController;
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }


}
