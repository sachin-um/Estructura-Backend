package com.Estructura.API.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        Optional<User> theUser=userRepository.findByEmail(user.getEmail());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException("A user with" +user.getEmail() +"already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users= userRepository.findAll();
        if (!users.isEmpty()){
            return ResponseEntity.ok(users);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<List<User>> findByRole(Role role) {
        List<User> users=userRepository.findByRole(role);
        if (!users.isEmpty()){
            return ResponseEntity.ok(users);
        }
        else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void resetUserPassword(User user, String newPassword) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
    }

}
