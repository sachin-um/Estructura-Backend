package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        Optional<User> theUser=userRepository.findByEmail(user.getEmail());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException("A user with" +user.getEmail() +"already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
