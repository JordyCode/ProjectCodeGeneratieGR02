package io.swagger.api.repository;

import io.swagger.api.model.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByID(Long userId);

    Account findByIBAN (String IBAN);


}
