package io.swagger.api.service;

import io.swagger.api.model.DTO.BalanceDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import io.swagger.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private BalanceDTO balanceDTO = new BalanceDTO();


    public Account add(Account account, boolean randomIBAN) {
        try{
            // Check if a random iban should be generated. If so, save it
            if (randomIBAN) {
                account.setIBAN(getIBAN());
            }

            accountRepository.save(account);
            return account;
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Incorrect iban given");
        }
    }

    public String getIBAN() {
        // Generate a new IBAN using dependency Iban4j
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("INHO").buildRandom();

        // Check if the IBAN is not in use already
        // If the IBAN is already in use, keep looping till there is a unique IBAN
        while (existByIBAN(iban.toString())) {
            iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("INHO").buildRandom();
        }
        return iban.toString();
    }

    public List<Account> getAllAccounts() {
        if (accountRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No accounts found");
        }
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        if (accountRepository.getAccountById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id not found");
        }
        return accountRepository.getAccountById(id);
    }

    public boolean checkIfAccountIsUser(Long id, User user) {
        if(!accountRepository.existsByIdAndUser(id, user))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account does not belong to user");
        }
        return accountRepository.existsByIdAndUser(id, user);
    }

    public Account save(Account users) {
        if (accountRepository.getAccountById(users.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No account found");
        }
        return accountRepository.save(users);
    }

    public BalanceDTO totalBalance(List<Account> accounts){
        double totalBalance = 0.00;

        if (accounts.size() < 1) {
            throw new IllegalArgumentException();
        }
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        balanceDTO.setTotalBalance(totalBalance);
        return balanceDTO;
    }

    public List<Account> getAccountsByUser(User user) {
        return accountRepository.getAccountByUser(user);
    }
    public boolean existByIBAN(String iban) {
        return accountRepository.existsByIBAN(iban);
    }
}
