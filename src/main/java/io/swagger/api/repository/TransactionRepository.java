package io.swagger.api.repository;

import io.swagger.api.model.DTO.TransactionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDTO, Long> {

}
