package io.swagger.api.repository;

import io.swagger.api.model.DTO.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDTO, Long> {

}
