package com.Estructura.API.service;

import com.Estructura.API.model.AccountStatus;
import com.Estructura.API.model.Role;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.users.UserUpdateRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);

    ResponseEntity<List<User>> getAllUsers();

    Optional<User> findByEmail(String email);

    ResponseEntity<List<User>> findByRole(Role role);

    Optional<User> findById(Integer id);

    GenericAddOrUpdateResponse<UserUpdateRequest> update(UserUpdateRequest userUpdateRequest, Integer id);

    ResponseEntity<User> verifyUser(Integer id);

    ResponseEntity<User> activeOrSuspendAcccount(Integer id,
        AccountStatus action);



    void resetUserPassword(User user, String newPassword);
}
