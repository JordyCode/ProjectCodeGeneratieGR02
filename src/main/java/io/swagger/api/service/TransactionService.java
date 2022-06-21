
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
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
        }
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllUserTransactions(User user) {
        if (transactionRepository.getTransactionByUser(user).isEmpty()) {
//            if (transactionRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user transactions found");
        }
        return transactionRepository.getTransactionByUser(user);
    }

    public Transaction getTransactionsById(Integer transactionId) {
        if (transactionRepository.findTransactionByTransactionId(transactionId) == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No transactions found");
        }
        return transactionRepository.findTransactionByTransactionId(transactionId);
    }

    public boolean checkIfTransactionBelongsToUser(User user, Integer transactionId) {
        if(!transactionRepository.existsByUserAndTransactionId(user, transactionId))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Transactions does not belong to user");
        }
        return transactionRepository.existsByUserAndTransactionId(user, transactionId);
    }

    public Transaction addTransaction(Transaction transaction) {
        Account sender = accountRepository.findByIBAN(transaction.getAccountFrom());
        Account receiver = accountRepository.findByIBAN(transaction.getAccountTo());
        User user = transaction.getUser();
        User performedBy = userRepository.getUserByUserId(transaction.getPerformedBy().longValue());

        //check if the sender and receiver IBANs exist and the accounts haven't been closed
        if (sender == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender IBAN does not exist!");
        } else if (receiver == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The receiver IBAN does not exist!");
        }
        else if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The receiver IBAN does not exist!");
        }

        if (sender.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender's account has been closed!");
        } else if (receiver.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The receiver's account has been closed!");
        }

        if(Objects.equals(sender.getIBAN(), receiver.getIBAN()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot transfer money to the same bank account");
        }

        //Savings accounts are not allowed to send transactions to accounts that don't belong to the same user. Users are not allowed to send transactions to savings accounts that don't belong to them.
        if(!(Objects.equals(sender.getUser().getUserId().intValue(), receiver.getUser().getUserId().intValue()))) {
            if (sender.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Savings accounts can only send transactions to accounts that belong to the same user!");
            } else if (receiver.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Transactions are not allowed to be made to savings accounts that don't belong to the same user!");
            }

        }

        if (Objects.isNull(performedBy))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User performing does not exist");
        }

        //Checks if user performing the transaction is the one sending and if the user performing the transaction is an employee
        if (!(performedBy.getRoles().contains(Role.ROLE_EMPLOYEE)) && !(Objects.equals(transaction.getPerformedBy(), sender.getUser().getUserId().intValue())) )
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not allowed to transfer money from another user's account");
        }

        if (transaction.getAmount() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Transaction amount has not been filled");
        }

        if (!(transaction.getAmount() <= sender.getUser().getTransactionLimit())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction amount exceeds transaction limit for user!");
        }

        Long total = transactionRepository.getTransactionsTotalByUser(sender.getUser().getUserId());
        if (total == null){
            total = 0L;
        }

        if (!(total + transaction.getAmount() <= sender.getUser().getDayLimit())) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Transaction exceeds the daily limit.");
        }

        //check if the sender isn't attempting an illegal transaction and doesn't have insufficient funds to complete the transaction, register this transaction to the database
        if (!(transaction.getAmount() <= 0.00)) {
            if ((sender.getBalance() - transaction.getAmount()) >= sender.getAbsoluteLimit()) {
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                receiver.setBalance(receiver.getBalance() + transaction.getAmount());
                transactionRepository.save(transaction);
                accountRepository.save(sender);
                accountRepository.save(receiver);
            }
            else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient funds!");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Zero or negative amounts are not allowed!");
        }
        return transaction;
    }


    public Transaction addWithdrawTransaction(Transaction withdraw)
    {
        Account user = accountRepository.findByIBAN(withdraw.getAccountFrom());
        User performedBy = userRepository.getUserByUserId(withdraw.getPerformedBy().longValue());

        if (Objects.isNull(performedBy))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User performing does not exist");
        }

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.GONE, "The sender IBAN does not exist!");
        }

        if (user.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Cannot withdraw from an inactive account");
        }

        if (user.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Cannot withdraw from a saving's account");
        }

        if (!(Objects.equals(withdraw.getPerformedBy(), user.getUser().getUserId().intValue())) )
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only the user who the account belongs to is allowed to withdraw");
        }

        if (withdraw.getAmount() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Transaction amount has not been filled");
        }

        if (!(withdraw.getAmount() <= user.getUser().getTransactionLimit())){
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Withdraw amount exceeds transaction limit for user!");
        }

        if (!(withdraw.getAmount() <= 0.00)) {
            if (!(withdraw.getAmount() <= 9.99)){
                if (!((user.getBalance() - withdraw.getAmount()) < user.getAbsoluteLimit())) {
                    user.setBalance(user.getBalance() - withdraw.getAmount());
                    transactionRepository.save(withdraw);
                    accountRepository.save(user);
                }
                else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Insufficient funds!");
                }
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot withdraw an amount lower than 10 euro");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Zero or negative amounts are not allowed!");
        }
        return withdraw;
    }

    public Transaction addDepositTransaction(Transaction deposit)
    {
        Account user = accountRepository.findByIBAN(deposit.getAccountTo());
        User performedBy = userRepository.getUserByUserId(deposit.getPerformedBy().longValue());

        if (Objects.isNull(performedBy))
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User performing does not exist");
        }
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.GONE, "The sender IBAN does not exist!");
        }

        if (user.getAccountStatus() == Account.AccountStatusEnum.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.LOCKED, "Cannot deposit into an inactive account");
        }

        if (user.getAccountType() == Account.AccountTypeEnum.SAVINGS) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Cannot deposit into a saving's account");
        }

        if (!(Objects.equals(deposit.getPerformedBy(), user.getUser().getUserId().intValue())))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only the user who the account belongs to is allowed to deposit");
        }

        if (deposit.getAmount() == null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Transaction amount has not been filled");
        }

        if (!(deposit.getAmount() <= 0.00)) {
            if (!(deposit.getAmount() <= 9.99)){
                user.setBalance(user.getBalance() + deposit.getAmount());
                transactionRepository.save(deposit);
                accountRepository.save(user);
            }
            else {
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Cannot deposit an amount lower than 10 euro");
            }
            user.setBalance(user.getBalance() + deposit.getAmount());
            transactionRepository.save(deposit);
            accountRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Zero or negative amounts are not allowed!");
        }
        return deposit;
    }

}





