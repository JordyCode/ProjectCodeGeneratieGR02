package io.swagger.api.service;

import io.swagger.api.jwt.JwtTokenProvider;
import io.swagger.api.model.Entity.User;
import io.swagger.api.model.Role;
import io.swagger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public String login(String username, String password) {

        String token = "";

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            token = jwtTokenProvider.createToken(username, user.getRoles());
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username/password");
        }

        return token;
    }

    public User add(User user, boolean isEmployee) {
        // Check if the user already exist
        if (userRepository.findByUsername(user.getUsername()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            if (isEmployee) {
                user.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_USER));
            } else {
                user.setRoles(List.of(Role.ROLE_USER));
            }

            user.setAccountStatus(User.AccountStatusEnum.ACTIVE);

            userRepository.save(user);
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username is already in use");
        }
    }
    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username not found");
        }
    }

    public List<User> getAllUsers() {
        if (userRepository.findAll().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No users found");
        }
        return userRepository.findAll();
    }

    public User findByUsername(String name) {
        if (userRepository.findByUsername(name) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username not found");
        }
        return userRepository.findByUsername(name);
    }

    public User getSpecificUser(Long userId) {
        if (userRepository.getUserByUserId(userId) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Id not found");
        }
        return userRepository.getUserByUserId(userId);
    }

    public List<User> getUsersByAccountsIsNull() {
        if (userRepository.getUsersByAccountsIsNull().size() == 0) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No users without any account found");
        }
        return userRepository.getUsersByAccountsIsNull();
    }
}
