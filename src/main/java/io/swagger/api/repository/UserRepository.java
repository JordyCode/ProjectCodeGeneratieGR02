package io.swagger.api.repository;

import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    User getUserById(UUID id);
}
