package io.swagger.api.service;

import io.swagger.api.controller.LoginApiController;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.AccountRepository;
import io.swagger.api.repository.TransactionRepository;
import io.swagger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Value("${bank.iban}")
    private String bankIban;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private LoginApiController loginApiController;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository, LoginApiController loginApiController) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.loginApiController = loginApiController;
    }

    public List<Transaction> getAllTransactions() {
        if (transactionRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No transactions found");
        }
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllUserTransactions(User user) {
        if (transactionRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No user transactions found");
        }
        return transactionRepository.getTransactionByUser(user);
    }

    public List<Transaction> getAllUserTransactionsToday(User user) {
        if (transactionRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No user transactions found");
        }
        return transactionRepository.getTransactionByUser(user);
    }



    public Transaction getTransactionsById(Integer transactionId) {
        if (transactionRepository.findTransactionByTransactionId(transactionId) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No transactions found");
        }
        return transactionRepository.findTransactionByTransactionId(transactionId);
    }
    public boolean checkIfTransactionBelongsToUser(User user, Integer transactionId) {
        if(!transactionRepository.existsByUserAndTransactionId(user, transactionId))
        {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Transactions does not belong to user");
        }
        return transactionRepository.existsByUserAndTransactionId(user, transactionId);
    }

    public Transaction addTransaction(Transaction transaction) {
        Account sender = accountRepository.findByIBAN(transaction.getAccountFrom());
        Account receiver = accountRepository.findByIBAN(transaction.getAccountTo());
        User senderLimit = userRepository.getUserByUserId(sender.getUser().getUserId());
        User performedBy = userRepository.getUserByUserId(transaction.getPerformedBy().longValue());
        Long total = transactionRepository.getTransactionsTotalByUser(sender.getUser().getUserId());
        if (total == null){
            total = 0L;
        }



        //check if the sender and receiver IBANs exist and the accounts haven't been closed
        if (sender == null || sender.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender IBAN does not exist or the account has been closed!");
        } else if (receiver == null || receiver.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The receiver IBAN does not exist or the account has been closed!");
        }

        if(Objects.equals(sender.getIBAN(), receiver.getIBAN()))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot transfer money to the same bank account");
        }

        //Savings accounts are not allowed to send transactions to accounts that don't belong to the same user. Users are not allowed to send transactions to savings accounts that don't belong to them.
        if(!(Objects.equals(sender.getUser().getUserId().intValue(), receiver.getUser().getUserId().intValue()))) {
            if (sender.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Savings accounts can only send transactions to accounts that belong to the same user!");
            } else if (receiver.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transactions are not allowed to be made to savings accounts that don't belong to the same user!");
            }

        }

        //Checks if user performing the transaction is the one sending and if the user performing the transaction is an employee
        if (!(performedBy.getRoles().contains(Role.ROLE_EMPLOYEE)) && !(Objects.equals(transaction.getPerformedBy(), sender.getUser().getUserId().intValue())) )
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is not allowed to transfer money from another user's account");
        }

        if (!(transaction.getAmount() <= sender.getUser().getTransactionLimit())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount exceeds transaction limit for user!");
        }

        if (!(total + transaction.getAmount() <= sender.getUser().getDayLimit())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction exceeds the daily limit.");
        }

//        if (!(transaction.getAmount() <= senderLimit.getDayLimit())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount exceeds total day limit for user!");
//        }

        //check if the sender isn't attempting an illegal transaction and doesn't have insufficient funds to complete the transaction, register this transaction to the database
        if (!(transaction.getAmount() <= 0.00)) {
           if (!((sender.getBalance() - transaction.getAmount()) < sender.getAbsoluteLimit())) {
                sender.setBalance(sender.getBalance() - transaction.getAmount());
//                receiver.setBalance(receiver.getBalance() + transaction.getAmount());
//                senderLimit.setDayLimit(senderLimit.getDayLimit() - transaction.getAmount());
                transactionRepository.save(transaction);
                accountRepository.save(sender);
                accountRepository.save(receiver);
//                userRepository.save(senderLimit);
            }
            else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zero or negative amounts are not allowed!");
        }
//                    if(transaction.getAmount() <= senderLimit.getTransactionLimit()){
//
//            }
//            else {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: exceeding transaction limit !");
//            }
        return transaction;
    }

    public Transaction addTransactionTEST(Transaction transaction) {
        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction addDeposit(Transaction transaction)
    {
        transactionRepository.save(transaction);
        return transaction;
    }

}

