package com.Estructura.API.service;

import static com.Estructura.API.model.Role.ADMIN;
import static com.Estructura.API.model.Role.ARCHITECT;
import static com.Estructura.API.model.Role.CARPENTER;
import static com.Estructura.API.model.Role.CONSTRUCTIONCOMPANY;
import static com.Estructura.API.model.Role.CUSTOMER;
import static com.Estructura.API.model.Role.INTERIORDESIGNER;
import static com.Estructura.API.model.Role.LANDSCAPEARCHITECT;
import static com.Estructura.API.model.Role.MASONWORKER;
import static com.Estructura.API.model.Role.PAINTER;
import static com.Estructura.API.model.Role.RENTER;
import static com.Estructura.API.model.Role.RETAILSTORE;
import static com.Estructura.API.model.ServiceProviderType.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.Estructura.API.config.JwtService;
import com.Estructura.API.model.Admin;
import com.Estructura.API.model.Architect;
import com.Estructura.API.model.Carpenter;
import com.Estructura.API.model.ConstructionCompany;
import com.Estructura.API.model.Customer;
import com.Estructura.API.model.InteriorDesigner;
import com.Estructura.API.model.LandscapeArchitect;
import com.Estructura.API.model.MasonWorker;
import com.Estructura.API.model.Painter;
import com.Estructura.API.model.Professional;
import com.Estructura.API.model.Qualification;
import com.Estructura.API.model.Renter;
import com.Estructura.API.model.RetailStore;
import com.Estructura.API.model.ServiceArea;
import com.Estructura.API.model.Specialization;
import com.Estructura.API.model.Token;
import com.Estructura.API.model.TokenType;
import com.Estructura.API.model.User;
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

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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

    public RegisterResponse register(RegisterRequest request, boolean preVerified) {
        var response = new RegisterResponse();

        // Pre check fields that aren't checked by response.checkValidity()
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            response.addError("email", "Email is already taken");
        }

        // Save tokens and user to database if user information is valid
        if (response.checkValidity(request)) {
            User user = null;
            User savedUser = null;
            List<String> qualifications;
            List<String> specializations = null;
            // List<String> serviceAreas;
            String ProfileImageName = null;
            if (request.getProfileImage() != null) {
                ProfileImageName = StringUtils.cleanPath(request.getProfileImage().getOriginalFilename());
            }
            if (request.getRole().equals(CUSTOMER)) {
                Customer customer = Customer.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .contactNo(request.getContactNo())
                        .addressLine1(request.getAddressLine1())
                        .addressLine2(request.getAddressLine2())
                        .city(request.getCity())
                        .district(request.getDistrict())
                        .role(request.getRole())
                        .build();
                user = customer;
                savedUser = customerService.saveCustomer(customer);
            } else if (request.getRole().equals(ADMIN)) {
                Admin admin = Admin.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .assignedArea(request.getAssignedArea())
                        .build();
                user = admin;
                savedUser = adminService.saveAdmin(admin);
            } else if (request.getRole().equals(RETAILSTORE)) {
                RetailStore retailStore = RetailStore.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .serviceProviderType(RETAILER)
                        .businessName(request.getBusinessName())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessCategory(request.getBusinessCategory())
                        .registrationNo(request.getRegistrationNo())
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .build();
                if (ProfileImageName != null) {
                    retailStore.setProfileImage(ProfileImageName);
                    retailStore.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = retailStore;
                savedUser = retailStoreService.saveRetailStore(retailStore);
            } else if (request.getRole().equals(RENTER)) {
                Renter renter = Renter.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .serviceProviderType(RENTINGCOMPANY)
                        .businessName(request.getBusinessName())
                        .businessContactNo(request.getBusinessContactNo())
                        .registrationNo(request.getRegistrationNo())
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .build();
                if (ProfileImageName != null) {
                    renter.setProfileImage(ProfileImageName);
                    renter.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = renter;
                savedUser = renterService.saveRenter(renter);
            } else if (request.getRole().equals(LANDSCAPEARCHITECT)) {
                LandscapeArchitect landscapeArchitect = LandscapeArchitect.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessName(request.getBusinessName())
                        .businessContactNo(request.getBusinessContactNo())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .sLIARegNumber(request.getSLIARegNumber())
                        .build();
                if (ProfileImageName != null) {
                    landscapeArchitect.setProfileImage(ProfileImageName);
                    landscapeArchitect.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = landscapeArchitect;
                savedUser = landscapeArchitectService.saveLandscapeArchitect(landscapeArchitect);
            } else if (request.getRole().equals(ARCHITECT)) {
                Architect architect = Architect.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessName(request.getBusinessName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .sLIARegNumber(request.getSLIARegNumber())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (ProfileImageName != null) {
                    architect.setProfileImage(ProfileImageName);
                    architect.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = architect;
                savedUser = architectService.saveArchitect(architect);
            } else if (request.getRole().equals(CONSTRUCTIONCOMPANY)) {
                ConstructionCompany constructionCompany = ConstructionCompany.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .businessRegNumber(request.getRegistrationNo())
                        .businessName(request.getBusinessName())
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getAddressLine2())
                        .businessContactNo(request.getBusinessContactNo())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .role(request.getRole())
                        .serviceProviderType(PROFESSIONAL)
                        .nic(request.getNic())
                        .teamSize(request.getTeamSize())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .teamSize(request.getTeamSize())
                        .isVerified(preVerified)
                        .build();
                if (ProfileImageName != null) {
                    constructionCompany.setProfileImage(ProfileImageName);
                    constructionCompany.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = constructionCompany;
                savedUser = constructionCompanyService.saveConstructionCompany(constructionCompany);
            } else if (request.getRole().equals(INTERIORDESIGNER)) {
                InteriorDesigner interiorDesigner = InteriorDesigner.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessName(request.getBusinessName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .sLIDRegNumber(request.getSLIDRegNumber())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (ProfileImageName != null) {
                    interiorDesigner.setProfileImage(ProfileImageName);
                    interiorDesigner.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = interiorDesigner;
                savedUser = interiorDesignerService.saveInteriorDesigner(interiorDesigner);
            } else if (request.getRole().equals(MASONWORKER)) {
                MasonWorker masonWorker = MasonWorker.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessName(request.getBusinessName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (ProfileImageName != null) {
                    masonWorker.setProfileImage(ProfileImageName);
                    masonWorker.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = masonWorker;
                savedUser = masonWorkerService.saveMasonWorker(masonWorker);
            } else if (request.getRole().equals(PAINTER)) {
                Painter painter = Painter.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessName(request.getBusinessName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (ProfileImageName != null) {
                    painter.setProfileImage(ProfileImageName);
                    painter.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = painter;
                savedUser = painterService.savePainter(painter);
            } else if (request.getRole().equals(CARPENTER)) {
                Carpenter carpenter = Carpenter.builder()
                        .isVerified(preVerified)
                        .firstname(request.getFirstname())
                        .lastname(request.getLastname())
                        .email(request.getEmail())
                        .businessContactNo(request.getBusinessContactNo())
                        .businessName(request.getBusinessName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
                        .nic(request.getNic())
                        .serviceProviderType(PROFESSIONAL)
                        .addressLine1(request.getBusinessAddressLine1())
                        .addressLine2(request.getBusinessAddressLine2())
                        .city(request.getBusinessCity())
                        .district(request.getBusinessDistrict())
                        .minRate(request.getMinRate())
                        .maxRate(request.getMaxRate())
                        .build();
                if (ProfileImageName != null) {
                    carpenter.setProfileImage(ProfileImageName);
                    carpenter.setProfileImageName(FileUploadUtil.generateFileName(ProfileImageName));
                }
                user = carpenter;
                savedUser = carpenterService.saveCarpenter(carpenter);
            }

            if (savedUser != null) {
                if (request.getSpecialization()!=null && !request.getSpecialization().isBlank()){
                    specializations= Arrays.stream(request.getSpecialization().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    User finalSavedUSer=savedUser;
                    specializations.forEach(specialization->{
                        saveSpecialization(finalSavedUSer,specialization);
                    });
                }
                if (request.getQualification()!=null && !request.getQualification().isBlank()){
                    qualifications=Arrays.stream(request.getQualification().split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                    User finalSavedUser = savedUser;
                    qualifications.forEach(qualification->{
                        saveQualification(finalSavedUser,qualification);
                    });
                }
                if (request.getServiceAreas()!=null){
                    User finalSavedUser = savedUser;
                    request.getServiceAreas().forEach(serviceArea->{
                        saveServiceArea(finalSavedUser,serviceArea);
                    });
                }
                var jwtToken= jwtService.generateToken(user);
                var refreshToken= jwtService.generateRefreshToken(user);
                saveUserToken(savedUser, jwtToken);
                if (savedUser.getProfileImageName()!=null){
                    String uploadDir = "./files/proflie-images/" + savedUser.getId();
                    try {
                        FileUploadUtil.saveFile(uploadDir,request.getProfileImage(),savedUser.getProfileImageName());
                    } catch (IOException e) {
                        System.out.println("Error saving file");
                    }
                }
                response.setLoggedUser(savedUser);
                response.setRole(savedUser.getRole());
                response.setAccessToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setSuccess(true);
            }
            else {
                response.setSuccess(false);
                response.setErrormessage("Something went wrong please try again");
            }
        }

        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var response = new AuthenticationResponse();
        var user = userService.findByEmail(request.getEmail())
                .orElse(null);
        if (user == null) {
            response.addError("email", "Email does not exist");
        } else if (!user.isVerified()) {
            response.addError("account", "Email is not verified");
        } else if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.addError("password", "Password is incorrect");
        } else {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()));
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtToken);
                response.setSuccess(true);
                response.setAccessToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setId(user.getId());
                response.setRole(user.getRole());
                response.setFirstname(user.getFirstname());
                response.setLastname(user.getLastname());
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
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void saveQualification(User user, String qualification) {
        var theQualification = Qualification.builder()
                .qualification(qualification)
                .build();
        if (user.getRole().equals(ARCHITECT)) {
            theQualification.setArchitect((Architect) user);
        }
        qualificationRepository.save(theQualification);
    }

    private void saveServiceArea(User user, String serviceArea) {
        var theServiceArea = ServiceArea.builder()
                .serviceArea(serviceArea)
                .build();
        if (user.getRole().equals(ARCHITECT)) {
            theServiceArea.setProfessional((Professional) user);
        }
        serviceAreaRepository.save(theServiceArea);
    }

    private void saveSpecialization(User user, String specialization) {
        var theSpecialization = Specialization.builder()
                .specialization(specialization)
                .build();
        if (user.getRole().equals(ARCHITECT)) {
            theSpecialization.setArchitect((Architect) user);
        }
        specializationRepository.save(theSpecialization);
    }

    public RefreshTokenResponse refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return RefreshTokenResponse.builder()
                    .success(false)
                    .message("Refresh token is missing")
                    .build();
        }
        refreshToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.userService.findByEmail(userEmail)
                        .orElse(null);
                if (user == null) {
                    return RefreshTokenResponse.builder()
                            .success(false)
                            .message("User not found")
                            .build();
                } else if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    return RefreshTokenResponse.builder()
                            .success(true)
                            .access_token(accessToken)
                            .refresh_token(refreshToken)
                            .build();
                } else {
                    return RefreshTokenResponse.builder()
                            .success(false)
                            .message("Refresh token is invalid")
                            .build();
                }
            } else {
                return RefreshTokenResponse.builder()
                        .success(false)
                        .message("Refresh token is invalid")
                        .build();
            }
        } catch (ExpiredJwtException e) {
            return RefreshTokenResponse.builder()
                    .success(false)
                    .message("Refresh token is expired")
                    .build();
        }
    }
}
