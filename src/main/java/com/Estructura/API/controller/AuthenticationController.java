package com.Estructura.API.controller;

import com.Estructura.API.event.PasswordResetEvent;
import com.Estructura.API.event.RegistrationCompleteEvent;
import com.Estructura.API.event.listener.RegistrationCompleteEventListener;
import com.Estructura.API.model.User;
import com.Estructura.API.model.VerificationToken;
import com.Estructura.API.requests.auth.AuthenticationRequest;
import com.Estructura.API.requests.auth.PasswordResetRequest;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.requests.auth.ResetPasswordRequest;
import com.Estructura.API.responses.GenericResponse;
import com.Estructura.API.responses.auth.AuthenticationResponse;
import com.Estructura.API.responses.auth.RefreshTokenResponse;
import com.Estructura.API.responses.auth.RegisterResponse;
import com.Estructura.API.service.AuthenticationService;
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
import org.springframework.web.servlet.view.RedirectView;

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
    public ResponseEntity<RegisterResponse> register(
        @ModelAttribute RegisterRequest request,
        final HttpServletRequest servletRequest) {
        RegisterResponse response = service.register(request, false);

        // Only send email with verify link if saving user is successful
        if (response.isSuccess()) {
            publisher.publishEvent(
                new RegistrationCompleteEvent(response.getLoggedUser(),
                                              applicationUrl(servletRequest)
                ));
            response.setLoggedUser(null); // Don't send user info back to client
            return ResponseEntity.ok(response);
        }
        // If saving user is not successful, return bad request
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/verifyEmail")
    public RedirectView verifyEmail(@RequestParam("token") String token) {
        RedirectView redirect = new RedirectView();
        System.out.println(token);
        VerificationToken theToken = verificationTokenService.findByToken(
            token);
        if (theToken.getUser().isVerified()) {
            System.out.println(
                "Email already verified. Now you can login to your account");
            redirect.setUrl("http://localhost:3000/emailVerifiec");
        }
        String verificationResult =
            verificationTokenService.validateVerificationToken(
            token);
        if (verificationResult.equalsIgnoreCase("valid")) { // if expired
            System.out.println(
                "Email verified successfully. Now you can login to your " +
                "account");
            redirect.setUrl("http://localhost:3000/emailVerified");
        } else {
            String url = applicationUrl(servletRequest) +
                         "/api/v1/auth/resend-verification-email?token=" +
                         theToken.getToken();
            System.out.println("Invalid verification link,<a href=\"" + url +
                               "\">Get a new Verification Email.</a>");
            redirect.setUrl(
                "http://localhost:3000/unauthorized"); // ! URLS should be
            // changed
        }
        return redirect;
    }

    @PostMapping("/password-reset-request")
    public GenericResponse<PasswordResetRequest> resetPasswordRequest(
        @RequestBody PasswordResetRequest passwordResetRequest) {
        GenericResponse<PasswordResetRequest> response =
            new GenericResponse<>();

        if (response.checkValidity(passwordResetRequest)) {
            Optional<User> user = userService.findByEmail(
                passwordResetRequest.getEmail());
            if (user.isPresent()) {
                publisher.publishEvent(new PasswordResetEvent(user.get(),
                                                              applicationUrl(
                                                                  servletRequest)
                ));
                response.setSuccess(true);
                response.setMessage(
                    "Password reset link has been sent to your email");
            } else {
                response.setSuccess(false);
                response.setMessage("User with this email does not exist");
            }
        }
        return response;
    }

    @PostMapping("/reset-password")
    public GenericResponse<ResetPasswordRequest> resetPassword(
        @RequestBody ResetPasswordRequest passwordResetRequest,
        @RequestParam("token") String passwordRestToken) {
        GenericResponse<ResetPasswordRequest> response =
            new GenericResponse<>();
        String verificationResult =
            verificationTokenService.validateVerificationToken(
            passwordRestToken);
        if (!verificationResult.equalsIgnoreCase("valid")) {
            response.addError("token", "Invalid token");
        }
        if (response.checkValidity(passwordResetRequest)) {
            Optional<User> user =
                verificationTokenService.findUserByPasswordRestToken(
                passwordRestToken);
            if (user.isPresent()) {
                userService.resetUserPassword(user.get(),
                                              passwordResetRequest.getNewPassword()
                );
                response.setSuccess(true);
                response.setMessage("You successfully reset your password");
            } else {
                response.addError("token", "Invalid token");
            }
        }
        return response;
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse register(
        @RequestBody AuthenticationRequest request) {
        return service.authenticate(request);
    }

    @GetMapping("/resend-verification-email")
    public String resendVerificationEmail(
        @RequestParam("token") String oldToken,
        final HttpServletRequest servletRequest) throws MessagingException,
        UnsupportedEncodingException {
        VerificationToken verificationToken =
            verificationTokenService.generateNewVerificationToken(
            oldToken);
        resendVerificationToken(applicationUrl(servletRequest),
                                verificationToken
        );
        return "A new verification link has been sent to your email, please " +
               "check your email to activate your";
    }

    private void resendVerificationToken(String applicationUrl,
        VerificationToken verificationToken) throws MessagingException,
        UnsupportedEncodingException {
        String url = applicationUrl + "/api/v1/auth/verifyEmail?token=" +
                     verificationToken.getToken();
        eventListener.sendEmailVerificationEmail(url);// handle exception
        log.info("Click the link to verify your Email : {}", url);
    }

    @PostMapping("/refresh-token")
    public RefreshTokenResponse refreshToken(HttpServletRequest request,
        HttpServletResponse response) {
        return service.refreshToken(request);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" +
               request.getServerPort() + request.getContextPath();
    }
}
