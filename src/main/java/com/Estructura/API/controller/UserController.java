package com.Estructura.API.controller;

import com.Estructura.API.model.User;
import com.Estructura.API.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }
}
