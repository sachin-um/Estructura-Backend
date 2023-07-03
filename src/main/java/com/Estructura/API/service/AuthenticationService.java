package com.Estructura.API.service;

import com.Estructura.API.auth.AuthenticationRequest;
import com.Estructura.API.auth.AuthenticationResponse;
import com.Estructura.API.auth.CustomerRegisterRequest;
import com.Estructura.API.auth.RegisterRequest;
import com.Estructura.API.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Estructura.API.config.JwtService;
import com.Estructura.API.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.Estructura.API.model.Role.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final CustomerService customerService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Role userRole=request.getRole();
        User user=null;
        System.out.println(userRole);
        if (userRole.equals(CUSTOMER)){
            if (request instanceof CustomerRegisterRequest) {
                CustomerRegisterRequest customerRegisterRequest = (CustomerRegisterRequest) request;
                Customer customer = Customer.builder()
                        .firstname(customerRegisterRequest.getFirstname())
                        .lastname(customerRegisterRequest.getLastname())
                        .email(customerRegisterRequest.getEmail())
                        .password(passwordEncoder.encode(customerRegisterRequest.getPassword()))
                        .role(userRole)
                        .addressLine1(customerRegisterRequest.getAddressLine1())
                        .addressLine2(customerRegisterRequest.getAddressLine2())
                        .city(customerRegisterRequest.getCity())
                        .district(customerRegisterRequest.getDistrict())
                        .build();

                user = customer;
                System.out.println("HI" + customer.toString());
            }
        }

        var response = new AuthenticationResponse();

        // Pre check fields that aren't checked by response.checkValidity()
        if(request.getPassword().isEmpty()) {
            response.addError("password", "Password is required");
        }

        // Save tokens and user to database if user information is valid
        if (user!=null && response.checkValidity(user)){
            User savedUser=null;
            if (user instanceof Customer){
                savedUser=customerService.saveCustomer((Customer) user);
            }
            var jwtToken= jwtService.generateToken(user);
            var refreshToken= jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);

            response.setLoggedUser(savedUser);
            response.setAccessToken(jwtToken);
            response.setRefreshToken(refreshToken);
            response.setSuccess(true);
        }
        
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user =userService.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.isVerified()){
            var jwtToken= jwtService.generateToken(user);
            var refreshToken= jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user,jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        else {
            return AuthenticationResponse.builder()
                    .errormessage("You have to verify your account first..")
                    .build();
        }

    }

    private void revokeAllUserTokens(User user){
        var validUserTokens=tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token= Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final  String userEmail;
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken=authHeader.substring(7);
        userEmail= jwtService.extractUsername(refreshToken);
        if (userEmail != null){
            var user=this.userService.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken,user)){
                var accessToken= jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user,accessToken);
                var authResponse=AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
