package io.swagger.api.repository;

import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Account getAccountById(UUID id);

    Account findByIBAN(String IBAN);

    boolean existsByIBAN(String IBAN);

    List<Account> getAccountByOwner(User user);

    boolean existsByIdAndOwner(UUID id, User owner);
}
