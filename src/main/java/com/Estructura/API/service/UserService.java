package com.Estructura.API.service;

import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.users.UserUpdateRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User saveUser(User user);
    public ResponseEntity<List<User>> getAllUsers();
    public Optional<User> findByEmail(String email);

    public ResponseEntity<List<User>> findByRole(Role role);
    public Optional<User> findById(Integer id);

//    GenericAddOrUpdateResponse<UserUpdateRequest>

    void resetUserPassword(User user, String newPassword);
}
