package io.swagger.api.service;

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
        // Generate a new IBAN
        Iban iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("CODE").buildRandom();

        // Check if the IBAN is not in use already
        // If the IBAN is already in use, keep looping till there is a unique IBAN
        while (existByIBAN(iban.toString())) {
            iban = new Iban.Builder().countryCode(CountryCode.NL).bankCode("CODE").buildRandom();
        }
        return iban.toString();
    }

    public List<Account> getAllAccounts() {
        if (accountRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No accounts found");
        }
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        if (accountRepository.getAccountById(id) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Id not found");
        }
        return accountRepository.getAccountById(id);
    }

    public boolean checkIfAccountIsOwner(Long id, User user) {
        if(!accountRepository.existsByIdAndUser(id, user))
        {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Account does not belong to owner");
        }
        return accountRepository.existsByIdAndUser(id, user);
    }

    public Account save(Account users) {
        if (accountRepository.getAccountById(users.getId()) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No accounts found");
        }
        return accountRepository.save(users);
    }

    public List<Account> getAccountsByUser(User user) {
        return accountRepository.getAccountByUser(user);
    }
    public boolean existByIBAN(String iban) {
        return accountRepository.existsByIBAN(iban);
    }
}
