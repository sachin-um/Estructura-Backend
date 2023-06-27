package com.Estructura.API.service;

import com.Estructura.API.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public List<User> getAllUsers();
    public Optional<User> findByEmail(String email);
}
