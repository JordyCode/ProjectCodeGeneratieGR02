package io.swagger.api.repository;

import io.swagger.api.model.Entity.Transaction;
import io.swagger.api.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Transaction findTransactionByTransactionId(Integer transactionId);

    boolean existsByUserAndTransactionId(User user, Integer transactionId);

    List<Transaction> getTransactionByUser(User user);

    @Query(
            value = "SELECT SUM(AMOUNT) as TOTAL_TRANSACTION_AMOUNT FROM TRANSACTION WHERE ACCOUNT_FROM IN (SELECT IBAN FROM ACCOUNT WHERE USER_USER_ID = :userId ) AND TIMESTAMP > NOW() - INTERVAL 1 DAY",
            nativeQuery = true)
    public Long getTransactionsTotalByUser(@Param("userId")Long userId);
}