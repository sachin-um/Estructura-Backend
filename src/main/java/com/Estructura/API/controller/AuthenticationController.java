package com.Estructura.API.controller;

import com.Estructura.API.auth.AuthenticationRequest;
import com.Estructura.API.auth.AuthenticationResponse;
import com.Estructura.API.event.RegistrationCompleteEvent;
import com.Estructura.API.model.EmailVerificationToken;
import com.Estructura.API.service.AuthenticationService;
import com.Estructura.API.auth.RegisterRequest;
import com.Estructura.API.service.EmailVerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final ApplicationEventPublisher publisher;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody RegisterRequest request,
            final HttpServletRequest servletRequest
    ){
        AuthenticationResponse response=service.register(request);
        // publisher.publishEvent(new RegistrationCompleteEvent(response.getLoggedUser(),applicationUrl(servletRequest)));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        EmailVerificationToken theToken=emailVerificationTokenService.findByToken(token);
        if (theToken.getUser().isVerified()){
            return "This account has already been verified ,please login";
        }
        String verificationResult=emailVerificationTokenService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        else {
            return "Invalid verification token";
            }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody AuthenticationRequest request
    ){

        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request,response);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
