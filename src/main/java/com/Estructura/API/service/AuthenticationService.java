package com.Estructura.API.service;

import com.Estructura.API.config.JwtService;
import com.Estructura.API.model.*;
import com.Estructura.API.repository.QualificationRepository;
import com.Estructura.API.repository.ServiceAreaRepository;
import com.Estructura.API.repository.SpecializationRepository;
import com.Estructura.API.repository.TokenRepository;
import com.Estructura.API.requests.auth.AuthenticationRequest;
import com.Estructura.API.requests.auth.RegisterRequest;
import com.Estructura.API.responses.auth.AuthenticationResponse;
import com.Estructura.API.responses.auth.RefreshTokenResponse;
import com.Estructura.API.responses.auth.RegisterResponse;
import com.Estructura.API.utils.FileUploadUtil;
import com.Estructura.API.utils.UserDetailsUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final RetailStoreService retailStoreService;
    private final RenterService renterService;
    private final ArchitectService architectService;
    private final LandscapeArchitectService landscapeArchitectService;
    private final ConstructionCompanyService constructionCompanyService;
    private final InteriorDesignerService interiorDesignerService;
    private final MasonWorkerService masonWorkerService;
    private final PainterService painterService;
    private final CarpenterService carpenterService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final QualificationRepository qualificationRepository;
    private final SpecializationRepository specializationRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final UserDetailsUtil userDetailsUtil;

    public RegisterResponse register(@ModelAttribute RegisterRequest request,
            boolean preVerified) {
        var response = new RegisterResponse();

        // Pre-check fields that aren't checked by response.checkValidity()
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            response.addError("email", "Email is already taken");
        }

        // Save tokens and user to database if user information is valid
        if (response.checkValidity(request)) {
            User user = null;
            User savedUser = null;
            List<String> qualifications;
            List<String> specializations;
            // List<String> serviceAreas;
            String profileImageName = null;
            if (request.getProfileImage() != null &&
                    request.getProfileImage().getOriginalFilename() != null) {
                profileImageName = StringUtils.cleanPath(
                        request.getProfileImage().getOriginalFilename());
            }
            if (request.getRole().equals(CUSTOMER)) {
                Customer customer = Customer.builder().isVerified(preVerified)
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail()).password(
                                passwordEncoder.encode(request.getPassword()))
                        .contactNo(request.getContactNo())
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .role(request.getRole()).build();
                user = customer;
                savedUser = customerService.saveCustomer(customer);
            } else if (request.getRole().equals(ADMIN)) {
                Admin admin = Admin.builder().isVerified(preVerified)
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail()).password(
                                passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .assignedArea(request.getAssignedArea())
                        .build();
                user = admin;
                savedUser = adminService.saveAdmin(admin);
            } else if (request.getRole().equals(RETAILSTORE)) {
                RetailStore retailStore = RetailStore.builder()
                        .isVerified(preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(
                                request.getLastName())
                        .email(request.getEmail())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(
                                RETAILER)
                        .businessName(
                                request.getBusinessName())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessCategory(
                                request.getBusinessCategory())
                        .registrationNo(
                                request.getRegistrationNo())
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(
                                request.getCity())
                        .district(
                                request.getDistrict())
                        .build();
                if (profileImageName != null) {
                    retailStore.setProfileImage(profileImageName);
                    retailStore.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = retailStore;
                savedUser = retailStoreService.saveRetailStore(retailStore);
            } else if (request.getRole().equals(RENTER)) {
                Renter renter = Renter.builder().isVerified(preVerified)
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail()).password(
                                passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(RENTINGCOMPANY)
                        .businessName(request.getBusinessName())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .registrationNo(
                                request.getRegistrationNo())
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .build();
                if (profileImageName != null) {
                    renter.setProfileImage(profileImageName);
                    renter.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = renter;
                savedUser = renterService.saveRenter(renter);
            } else if (request.getRole().equals(LANDSCAPEARCHITECT)) {
                LandscapeArchitect landscapeArchitect = LandscapeArchitect.builder()
                        .isVerified(
                                preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(
                                request.getLastName())
                        .email(
                                request.getEmail())
                        .businessName(
                                request.getBusinessName())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role(
                                request.getRole())
                        .nic(
                                request.getNic())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(
                                request.getCity())
                        .district(
                                request.getDistrict())
                        .minRate(
                                request.getMinRate())
                        .maxRate(
                                request.getMaxRate())
                        .sliaRegNumber(
                                request.getSliaRegNumber())
                        .build();
                if (profileImageName != null) {
                    landscapeArchitect.setProfileImage(profileImageName);
                    landscapeArchitect.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = landscapeArchitect;
                savedUser = landscapeArchitectService.saveLandscapeArchitect(
                        landscapeArchitect);
            } else if (request.getRole().equals(ARCHITECT)) {
                Architect architect = Architect.builder()
                        .isVerified(preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessName(
                                request.getBusinessName())
                        .password(passwordEncoder.encode(
                                request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .sliaRegNumber(
                                request.getSliaRegNumber())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (profileImageName != null) {
                    architect.setProfileImage(profileImageName);
                    architect.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = architect;
                savedUser = architectService.saveArchitect(architect);
            } else if (request.getRole().equals(CONSTRUCTIONCOMPANY)) {
                ConstructionCompany constructionCompany = ConstructionCompany.builder()
                        .isVerified(
                                preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(
                                request.getLastName())
                        .email(
                                request.getEmail())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .businessRegNumber(
                                request.getRegistrationNo())
                        .businessName(
                                request.getBusinessName())
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .city(
                                request.getCity())
                        .district(
                                request.getDistrict())
                        .role(
                                request.getRole())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .nic(
                                request.getNic())
                        .minRate(
                                request.getMinRate())
                        .maxRate(
                                request.getMaxRate())
                        .build();
                if (profileImageName != null) {
                    constructionCompany.setProfileImage(profileImageName);
                    constructionCompany.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = constructionCompany;
                savedUser = constructionCompanyService.saveConstructionCompany(
                        constructionCompany);
            } else if (request.getRole().equals(INTERIORDESIGNER)) {
                InteriorDesigner interiorDesigner = InteriorDesigner.builder()
                        .isVerified(
                                preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(
                                request.getLastName())
                        .email(
                                request.getEmail())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessName(
                                request.getBusinessName())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role(
                                request.getRole())
                        .nic(
                                request.getNic())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(
                                request.getCity())
                        .district(
                                request.getDistrict())
                        .sliaRegNumber(
                                request.getSlidRegNumber())
                        .minRate(
                                request.getMinRate())
                        .maxRate(
                                request.getMaxRate())
                        .build();
                if (profileImageName != null) {
                    interiorDesigner.setProfileImage(profileImageName);
                    interiorDesigner.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = interiorDesigner;
                savedUser = interiorDesignerService.saveInteriorDesigner(
                        interiorDesigner);
            } else if (request.getRole().equals(MASONWORKER)) {
                MasonWorker masonWorker = MasonWorker.builder()
                        .isVerified(preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(
                                request.getLastName())
                        .email(request.getEmail())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessName(
                                request.getBusinessName())
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(
                                request.getCity())
                        .district(
                                request.getDistrict())
                        .minRate(
                                request.getMinRate())
                        .maxRate(
                                request.getMaxRate())
                        .build();
                if (profileImageName != null) {
                    masonWorker.setProfileImage(profileImageName);
                    masonWorker.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = masonWorker;
                savedUser = masonWorkerService.saveMasonWorker(masonWorker);
            } else if (request.getRole().equals(PAINTER)) {
                Painter painter = Painter.builder().isVerified(preVerified)
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessName(
                                request.getBusinessName())
                        .password(passwordEncoder.encode(
                                request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(request.getCity())
                        .district(
                                request.getDistrict())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate()).build();
                if (profileImageName != null) {
                    painter.setProfileImage(profileImageName);
                    painter.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = painter;
                savedUser = painterService.savePainter(painter);
            } else if (request.getRole().equals(CARPENTER)) {
                Carpenter carpenter = Carpenter.builder()
                        .isVerified(preVerified)
                        .firstName(
                                request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .businessContactNo(
                                request.getBusinessContactNo())
                        .businessName(
                                request.getBusinessName())
                        .password(passwordEncoder.encode(
                                request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(
                                PROFESSIONAL)
                        .addressLine1(
                                request.getAddressLine1())
                        .addressLine2(
                                request.getAddressLine2())
                        .city(request.getCity())
                        .district(
                                request.getDistrict())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (profileImageName != null) {
                    carpenter.setProfileImage(profileImageName);
                    carpenter.setProfileImageName(
                            FileUploadUtil.generateFileName(profileImageName));
                }
                user = carpenter;
                savedUser = carpenterService.saveCarpenter(carpenter);
            }

            if (savedUser != null) {
                if (request.getSpecialization() != null &&
                        !request.getSpecialization().isBlank()) {
                    specializations = Arrays.stream(
                            request.getSpecialization().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    User finalSavedUSer = savedUser;
                    specializations.forEach(specialization -> {
                        userDetailsUtil.saveSpecialization(finalSavedUSer,
                            specialization);
                    });
                }
                if (request.getQualification() != null &&
                        !request.getQualification().isBlank()) {
                    qualifications = Arrays.stream(
                            request.getQualification().split(",")).map(String::trim)
                            .collect(Collectors.toList());
                    User finalSavedUser = savedUser;
                    qualifications.forEach(qualification -> {
                        userDetailsUtil.saveQualification(finalSavedUser,
                            qualification);
                    });
                }
                if (request.getServiceAreas() != null) {
                    User finalSavedUser = savedUser;
                    request.getServiceAreas().forEach(serviceArea -> {
                        userDetailsUtil.saveServiceArea(finalSavedUser,
                            serviceArea);
                    });
                }
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                saveUserToken(savedUser, jwtToken);
                if (savedUser.getProfileImageName() != null) {
                    String uploadDir = "./files/profile-images/" + savedUser.getId();
                    try {
                        FileUploadUtil.saveFile(uploadDir,
                                request.getProfileImage(),
                                savedUser.getProfileImageName());
                    } catch (IOException e) {
                        System.out.println("Error saving file");
                    }
                }
                response.setLoggedUser(savedUser);
                response.setRole(savedUser.getRole());
                response.setAccessToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMessage(
                        "Something went wrong please try again");
            }
        }

        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var response = new AuthenticationResponse();
        var user = userService.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            response.addError("email", "Email does not exist");
        } else if (!user.isVerified()) {
            response.addError("account", "Email is not verified");
        } else if (!passwordEncoder.matches(request.getPassword(),
                user.getPassword())) {
            response.addError("password", "Password is incorrect");
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(),
                                request.getPassword()));
                revokeAllUserTokens(user);
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                // ! Sometimes this is necessary for some reason
                // revokeAllUserTokens(user); // ! <-
                saveUserToken(user, jwtToken);
                response.setSuccess(true);
                response.setAccessToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setId(user.getId());
                response.setRole(user.getRole());
                if (user.getRole() == ARCHITECT ||
                        user.getRole() == CARPENTER ||
                        user.getRole() == INTERIORDESIGNER ||
                        user.getRole() == LANDSCAPEARCHITECT ||
                        user.getRole() == MASONWORKER ||
                        user.getRole() == PAINTER ||
                        user.getRole() == CONSTRUCTIONCOMPANY) {
                    response.setServiceProviderType(PROFESSIONAL);
                } else if (user.getRole() == RETAILSTORE) {
                    response.setServiceProviderType(RETAILER);
                } else if (user.getRole() == RENTER) {
                    response.setServiceProviderType(RENTINGCOMPANY);
                } else {
                    response.setServiceProviderType(null);
                }
                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
                response.setEmail(user.getEmail());
                response.setProfileImage(user.getProfileImage());
                response.setProfileImageName(user.getProfileImageName());
            } catch (Exception e) {
                response.setSuccess(false);
                response.addError("auth", "Authentication failed");
            }
        }
        return response;
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(
                user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().user(user).token(jwtToken)
                .tokenType(TokenType.BEARER).revoked(false)
                .expired(false).build();
        tokenRepository.save(token);
    }



    public RefreshTokenResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return RefreshTokenResponse.builder().success(false)
                    .message("Refresh token is missing")
                    .build();
        }
        refreshToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.userService.findByEmail(userEmail).orElse(null);
                if (user == null) {
                    return RefreshTokenResponse.builder().success(false)
                            .message("User not found")
                            .build();
                } else if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    return RefreshTokenResponse.builder().success(true)
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                } else {
                    return RefreshTokenResponse.builder().success(false)
                            .message(
                                    "Refresh token is invalid")
                            .build();
                }
            } else {
                return RefreshTokenResponse.builder().success(false)
                        .message("Refresh token is invalid")
                        .build();
            }
        } catch (ExpiredJwtException e) {
            return RefreshTokenResponse.builder().success(false)
                    .message("Refresh token is expired")
                    .build();
        }
    }

    /**
     * @param email
     * @return true if email is available
     */
    public boolean CheckEmailAvailable(String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.isEmpty();
    }
}
