package io.swagger.api.service;

import io.swagger.api.model.Entity.User;
import io.swagger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User u){
        return userRepository.save(u);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


}
