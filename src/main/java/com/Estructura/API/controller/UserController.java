package com.Estructura.API.controller;

import com.Estructura.API.model.User;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.requests.users.UserUpdateRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable("user_id") int id){
        Optional<User> user= userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
//    @PostMapping("update/{user_id}")
//    public GenericAddOrUpdateResponse<UserUpdateRequest> updateUser(@PathVariable("user_id") int id, @ModelAttribute UserUpdateRequest userUpdateRequest){
//
//    }
}
