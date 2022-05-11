package io.swagger.api.service;

import io.swagger.api.model.DTO.UserDTO;
import io.swagger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO add(UserDTO u){
        return userRepository.save(u);
    }

    public List<UserDTO> getAll(){
        return userRepository.findAll();
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {final UserDTO user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("UserDTO '" + username + "' not found");
//        }
//        return org.springframework.security.core.userdetails.UserDTO
//                .withUsername(username)
////                .password(user.getPassword())
////                .authorities(user.getRoles())
//                .accountExpired(false)
//                .accountLocked(false)
//                .credentialsExpired(false)
//                .disabled(false)
//                .build();
//    }


}
