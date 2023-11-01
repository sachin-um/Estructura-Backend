package com.Estructura.API.service;

import com.Estructura.API.config.JwtService;
import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.*;
import com.Estructura.API.repository.AdminRepository;
import com.Estructura.API.repository.TokenRepository;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.auth.RegisterResponse;
import com.Estructura.API.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Estructura.API.model.Role.*;
import static com.Estructura.API.model.ServiceProviderType.*;
import static com.Estructura.API.model.ServiceProviderType.PROFESSIONAL;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public Admin saveAdmin(Admin admin) {
        Optional<Admin> theAdmin = adminRepository.findByEmail(
            admin.getEmail());
        if (theAdmin.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + admin.getEmail() + "already exists");
        }
        return adminRepository.save(admin);
    }


    @Override
    public RegisterResponse crateAnAdmin(@ModelAttribute RegisterRequest registerRequest) {
        var response = new RegisterResponse();

        // Pre-check fields that aren't checked by response.checkValidity()
        if (userService.findByEmail(registerRequest.getEmail()).isPresent()) {
            response.addError("email", "Email is already taken");
        }

        // Save tokens and user to database if user information is valid
        if (response.checkValidity(registerRequest)) {
            User         user      = null;
            User         savedUser = null;
            List<String> qualifications;
            List<String> specializations;
            // List<String> serviceAreas;

             if (registerRequest.getRole().equals(ADMIN)) {
                Admin admin = Admin.builder()
                                   .isVerified(true)
                                   .firstName(registerRequest.getFirstName())
                                   .lastName(registerRequest.getLastName())
                                   .email(registerRequest.getEmail())
                                   .password(passwordEncoder.encode(
                                       registerRequest.getPassword()))
                                   .role(registerRequest.getRole())
                                   .assignedArea(registerRequest.getAssignedArea())
                                   .build();
                user      = admin;
                savedUser = adminRepository.save(admin);;
            }
            if (savedUser != null) {
                response.setSuccess(true);
                response.setLoggedUser(savedUser);
            } else {
                response.setSuccess(false);
                response.setErrorMessage(
                    "Something went wrong please try again");
            }
        }

        return response;
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().user(user).token(jwtToken)
                         .tokenType(TokenType.BEARER).revoked(false)
                         .expired(false).build();
        tokenRepository.save(token);
    }
}
