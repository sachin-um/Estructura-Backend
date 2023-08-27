package com.Estructura.API.service;

import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    ResponseEntity<List<User>> getAllUsers();

    Optional<User> findByEmail(String email);

    ResponseEntity<List<User>> findByRole(Role role);

    Optional<User> findById(Integer id);

    void resetUserPassword(User user, String newPassword);
}
