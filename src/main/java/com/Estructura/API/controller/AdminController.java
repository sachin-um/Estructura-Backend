package com.Estructura.API.controller;

import com.Estructura.API.model.AccountStatus;
import com.Estructura.API.model.User;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.AdminDemoResponse;
import com.Estructura.API.responses.auth.RegisterResponse;
import com.Estructura.API.service.AdminService;
import com.Estructura.API.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping
    public AdminDemoResponse get() {
        return new AdminDemoResponse(true, "Hello from admin");
    }

    @PutMapping("/verify-user/{user_id}")
    public ResponseEntity<User> verifyUser(@PathVariable("user_id") int user_id){
        return userService.verifyUser(user_id);
    }

    @PutMapping("/handle-status/{user_id}/{action}")
    public ResponseEntity<User> activateOrSuspendAccount(@PathVariable(
        "user_id") int user_id,@PathVariable("action")AccountStatus action){
        return userService.activeOrSuspendAcccount(user_id,action);
    }

    @PostMapping("/create-an-admin")
    public RegisterResponse createAnAdmin(@ModelAttribute RegisterRequest registerRequest){
        return adminService.crateAnAdmin(registerRequest);
    }
}
