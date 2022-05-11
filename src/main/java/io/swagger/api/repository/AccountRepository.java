package io.swagger.api.repository;

import io.swagger.api.model.DTO.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDTO, Long> {


}
