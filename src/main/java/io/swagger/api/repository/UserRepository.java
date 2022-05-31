package io.swagger.api.repository;

import io.swagger.api.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User getUserByUserId(Long userId);

    List<User> getUsersByAccountsIsNull();
}
