package io.swagger.api.service;

import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.model.Entity.User;
import io.swagger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public String login(String username, String password) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//
//            User user = userRepository.findByUsername(username);
//            return jwtTokenProvider.createToken(username, user.getRoles());
//        } catch (AuthenticationException exception) {
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username/password invalid");
//        }
//    }
//
//    public User add(User user, boolean employee) {
//        // Check if the user doesn't already exist
//        if (userRepository.findByUsername(user.getUsername()) == null) {
//
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//            if (employee) {
//                user.setRoles(Arrays.asList(Role.ROLE_EMPLOYEE, Role.ROLE_USER));
//                user.setAccessLevel(Arrays.asList(User.AccessLevelEnum.EMPLOYEE, User.AccessLevelEnum.USER));
//            } else {
//                user.setRoles(Arrays.asList(Role.ROLE_USER));
//                user.setAccessLevel(Arrays.asList(User.AccessLevelEnum.USER));
//            }
//
//            user.setStatus(Arrays.asList(User.StatusEnum.ACTIVE));
//
//            userRepository.save(user);
//            return user;
//        } else {
//            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Username already in use");
//        }
//    }

    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return userRepository.save(user);
        }
        else
        {
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

    public User getSpecificUser(UUID id) {
        if (userRepository.getUserById(id) == null) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Id not found");
        }
        return userRepository.getUserById(id);
    }

}
