package io.swagger.api.repository;

import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction getTransactionById(Long id);

    List<Transaction> getTransactionByOwner(User user);
}
