package com.Estructura.API.controller;

import com.Estructura.API.auth.AuthenticationRequest;
import com.Estructura.API.auth.AuthenticationResponse;
import com.Estructura.API.auth.PasswordResetRequest;
import com.Estructura.API.event.PasswordResetEvent;
import com.Estructura.API.event.RegistrationCompleteEvent;
import com.Estructura.API.event.listener.RegistrationCompleteEventListener;
import com.Estructura.API.model.User;
import com.Estructura.API.model.VerificationToken;
import com.Estructura.API.service.AuthenticationService;
import com.Estructura.API.auth.RegisterRequest;
import com.Estructura.API.service.UserService;
import com.Estructura.API.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService service;
    private final VerificationTokenService verificationTokenService;
    private final ApplicationEventPublisher publisher;
    private final RegistrationCompleteEventListener eventListener;
    private final HttpServletRequest servletRequest;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request,
            final HttpServletRequest servletRequest) {
        AuthenticationResponse response = service.register(request);
        
        // Only send email if saving user is successful
        if (response.isSuccess()) {
            publisher.publishEvent(new RegistrationCompleteEvent(
                    response.getLoggedUser(), applicationUrl(servletRequest)));
            return ResponseEntity.ok(response);
        }

        // If saving user is not successful, return bad request
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken= verificationTokenService.findByToken(token);
        String url=applicationUrl(servletRequest)+"/api/v1/auth/resend-verification-email?token="+theToken.getToken();
        if (theToken.getUser().isVerified()){
            return "This account has already been verified ,please login";
        }
        String verificationResult = verificationTokenService.validateVerificationToken(token);
        if (verificationResult.equalsIgnoreCase("valid")) { //if expired
            return "Email verified successfully. Now you can login to your account";
        }
        else {
            return "Invalid verification link,<a href=\""+url+"\">Get a new Verification Email.</a>";
        }

    }

    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest){
        Optional<User> user=userService.findByEmail(passwordResetRequest.getEmail());
        if (user.isPresent()){
                publisher.publishEvent(new PasswordResetEvent(user.get(),applicationUrl(servletRequest)));
                return "Password Rest Link is send to your Email";
        }
        else {
            return "Account not found";
        }
    }


    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody PasswordResetRequest passwordResetRequest,
                                @RequestParam("token") String passwordRestToken){
        String verificationResult=verificationTokenService.validateVerificationToken(passwordRestToken);
        String url=applicationUrl(servletRequest)+"/api/v1/auth/resend-verification-email?token="+passwordRestToken;
        if (!verificationResult.equalsIgnoreCase("valid")){
            return  "Token is not valid";
        }
        Optional<User> user=verificationTokenService.findUserByPasswordRestToken(passwordRestToken);
        if (user.isPresent()){
            userService.resetUserPassword(user.get(), passwordResetRequest.getNewPassword());
            return "You successfully reset your password";
        }
        return "Invalid verification link,<a href=\""+url+"\">Get a new Verification Email.</a>";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/resend-verification-email")
    public String resendVerificationEmail(@RequestParam("token") String oldToken,
                                          final HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException {
        VerificationToken verificationToken = verificationTokenService.generateNewVerificationToken(oldToken);
        resendVerificationToken(applicationUrl(servletRequest), verificationToken);
        return "A new verification link has been sent to your email, please check your email to activate your";
    }

    private void resendVerificationToken(String applicationUrl, VerificationToken verificationToken) throws MessagingException, UnsupportedEncodingException {
        String url=applicationUrl+"/api/v1/auth/verifyEmail?token="+ verificationToken.getToken();
        eventListener.sendEmailVerificationEmail(url);//handle exception
        log.info("Click the link to verify your Email : {}",url);

    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
