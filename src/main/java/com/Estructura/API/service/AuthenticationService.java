package com.Estructura.API.service;

import com.Estructura.API.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Estructura.API.config.JwtService;
import com.Estructura.API.repository.TokenRepository;
import com.Estructura.API.requests.auth.AuthenticationRequest;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.auth.AuthenticationResponse;
import com.Estructura.API.responses.auth.RegisterResponse;
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
    private final AdminService adminService;
    private final RetailStoreService retailStoreService;
    private final ArchitectService architectService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        var response = new RegisterResponse();

        // Pre check fields that aren't checked by response.checkValidity()
        if(userService.findByEmail(request.getEmail()).isPresent()) {
            response.addError("email", "Email is already taken");
        }


        // Save tokens and user to database if user information is valid
        if (response.checkValidity(request)){
            User user=null;
            User savedUser=null;
            if(request.getRole().equals(CUSTOMER)){
                Customer customer=Customer.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .addressLine1(request.getAddressLine1())
                        .addressLine2(request.getAddressLine2())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .build();
                user=customer;
                savedUser=customerService.saveCustomer(customer);
            }
            else if(request.getRole().equals(ADMIN)){
                Admin admin=Admin.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .assignedArea(request.getAssignedArea())
                        .build();
                user=admin;
                savedUser=adminService.saveAdmin(admin);
            }
            else if(request.getRole().equals(RETAILOWNER)){
                RetailStore retailStore=RetailStore.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .businessName(request.getBusinessName())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessCategory(request.getBusinessCategory())
                        .registrationNo(request.getRegistrationNo())
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .build();
                user=retailStore;
                savedUser=retailStoreService.saveRetailStore(retailStore);
            }
            else if (request.getRole().equals(ARCHITECT)){
                Architect architect=Architect.builder()
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(request.getServiceProviderType())
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .sLIARegNumber(request.getSLIARegNumber())
                        .build();
                user=architect;
                savedUser=architectService.saveArchitect(architect);
            }
            var jwtToken= jwtService.generateToken(user);
            var refreshToken= jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            response.setLoggedUser(savedUser);
            response.setRole(savedUser.getRole());
            response.setAccessToken(jwtToken);
            response.setRefreshToken(refreshToken);
            response.setSuccess(true);
        }
        
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var response = new AuthenticationResponse();
        var user =userService.findByEmail(request.getEmail())
                .orElse(null);
        if(user == null) {
            response.addError("email", "Email does not exist");
        } else if(!user.isVerified()) {
            response.addError("account", "Email is not verified");
        } else if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.addError("password", "Password is incorrect");
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );
                var jwtToken= jwtService.generateToken(user);
                var refreshToken= jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user,jwtToken);
                response.setAccessToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setRole(user.getRole());
                response.setSuccess(true);
            } catch (Exception e) {
                response.setSuccess(false);
                response.addError("auth", "Authentication failed");
            }
        }
        return response;
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
                var authResponse=RegisterResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }
}
