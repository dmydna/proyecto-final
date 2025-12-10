package com.techlab.store.service;

import com.techlab.store.entity.User;
import com.techlab.store.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository UserRepository;


    public User getUserById(Long id){
        Optional<User> userOptional = this.UserRepository.findById(id);

        if (userOptional.isEmpty()){
            throw new RuntimeException("User no encontrado con ID: " + id);
        }

        return userOptional.get();
    }

}
