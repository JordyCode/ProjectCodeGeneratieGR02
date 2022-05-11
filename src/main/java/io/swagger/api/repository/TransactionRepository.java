package io.swagger.api.repository;

import io.swagger.api.model.DTO.TransactionDTO;
import io.swagger.api.model.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
