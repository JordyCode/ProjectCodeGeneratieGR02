package io.swagger.api.repository;

import io.swagger.api.model.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByID(String IBAN);

    Account findByIBAN (String IBAN);


}
