package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.*;
import com.Estructura.API.repository.UserRepository;
import com.Estructura.API.requests.users.UserUpdateRequest;
import com.Estructura.API.responses.GenericAddOrUpdateResponse;
import com.Estructura.API.responses.GenericResponse;
import com.Estructura.API.utils.FileUploadUtil;
import com.Estructura.API.utils.UserDetailsUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Estructura.API.model.AccountType.PREMIUM;
import static com.Estructura.API.model.Role.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final RetailStoreService retailStoreService;
    private final RenterService renterService;
    private final UserDetailsUtil userDetailsUtil;
    private final ArchitectService architectService;

    @Override
    public User saveUser(User user) {
        Optional<User> theUser = userRepository.findByEmail(user.getEmail());
        if (theUser.isPresent()) {
            throw new UserAlreadyExistsException(
                "A user with" + user.getEmail() + "already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<List<User>> findByRole(Role role) {
        List<User> users = userRepository.findByRole(role);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public GenericAddOrUpdateResponse<UserUpdateRequest> update(UserUpdateRequest userUpdateRequest, Integer id) {
        GenericAddOrUpdateResponse<UserUpdateRequest> response=
            new GenericAddOrUpdateResponse<>();
        if (response.checkValidity(userUpdateRequest)){
            Optional<User> existingUser=userRepository.findById(id);
            if (existingUser.isPresent()){
                User savedUser = null;
                List<String> qualifications;
                List<String> specializations;
                String profileImageName = null;
                if (userUpdateRequest.getProfileImage() != null &&
                    userUpdateRequest.getProfileImage().getOriginalFilename() != null) {
                    profileImageName = StringUtils.cleanPath(
                        userUpdateRequest.getProfileImage().getOriginalFilename());
                }
                if (existingUser.get().getRole().equals(CUSTOMER)){
                    Optional<Customer> customer=
                        customerService.findById(existingUser.get().getId());
                    Customer existingCustomer=customer.get();
                    existingCustomer.setFirstName(
                        userUpdateRequest.getFirstName());
                    existingCustomer.setLastName(userUpdateRequest.getLastName());
                    existingCustomer.setContactNo(
                        userUpdateRequest.getContactNo());
                    existingCustomer.setAddressLine1(
                        userUpdateRequest.getAddressLine1());
                    existingCustomer.setAddressLine2(
                        userUpdateRequest.getAddressLine2());
                    existingCustomer.setCity(userUpdateRequest.getCity());
                    existingCustomer.setDistrict(userUpdateRequest.getDistrict());
                    savedUser=customerService.saveCustomer(existingCustomer);
                } else if (existingUser.get().getRole().equals(RETAILSTORE)) {
                    Optional<RetailStore> retailStore=
                        retailStoreService.findById(existingUser.get().getId());
                    RetailStore existingRetailStore=null;
                    if (retailStore.isPresent()){
                        existingRetailStore=retailStore.get();
                    }
                    if (existingRetailStore != null) {
                        existingRetailStore.setFirstName(
                            userUpdateRequest.getFirstName());
                        existingRetailStore.setLastName(
                            userUpdateRequest.getLastName());
                        existingRetailStore.setBusinessName(
                            userUpdateRequest.getBusinessName());
                        existingRetailStore.setBusinessCategory(userUpdateRequest.getBusinessCategory());
                        existingRetailStore.setAddressLine1(
                            userUpdateRequest.getBusinessAddressLine1());
                        existingRetailStore.setAddressLine2(
                            userUpdateRequest.getBusinessAddressLine2());
                        existingRetailStore.setCity(userUpdateRequest.getBusinessCity());
                        existingRetailStore.setDistrict(
                            userUpdateRequest.getBusinessDistrict());
                        existingRetailStore.setBusinessContactNo(
                            userUpdateRequest.getBusinessContactNo());
                        if (profileImageName != null) {
                            existingRetailStore.setProfileImage(profileImageName);
                            existingRetailStore.setProfileImageName(
                                FileUploadUtil.generateFileName(profileImageName));
                        }
                    }
                    savedUser=
                        retailStoreService.saveRetailStore(existingRetailStore);
                } else if (existingUser.get().getRole().equals(RENTER)) {
                    Optional<Renter> renter=
                        renterService.findById(existingUser.get().getId());
                    Renter existingRenter=null;
                    if (renter.isPresent()){
                        existingRenter=renter.get();
                    }
                    if (existingRenter!=null){
                        existingRenter.setFirstName(
                            userUpdateRequest.getFirstName());
                        existingRenter.setLastName(
                            userUpdateRequest.getLastName());
                        existingRenter.setBusinessName(
                            userUpdateRequest.getBusinessName());
                        existingRenter.setAddressLine1(
                            userUpdateRequest.getBusinessAddressLine1());
                        existingRenter.setAddressLine2(
                            userUpdateRequest.getBusinessAddressLine2());
                        existingRenter.setCity(
                            userUpdateRequest.getBusinessCity());
                        existingRenter.setDistrict(
                            userUpdateRequest.getDistrict());
                        existingRenter.setBusinessContactNo(
                            userUpdateRequest.getBusinessContactNo());
                        if (profileImageName != null) {
                            existingRenter.setProfileImage(profileImageName);
                            existingRenter.setProfileImageName(
                                FileUploadUtil.generateFileName(profileImageName));
                        }
                    }
                    savedUser=renterService.saveRenter(existingRenter);
                } else if (existingUser.get().getRole().equals(ARCHITECT)) {
                    Optional<Architect> architect=
                        architectService.findArchitectById(existingUser.get().getId());
                    Architect existingArchitect=null;
                    if (architect.isPresent()){
                        existingArchitect=architect.get();
                    }
                    if (existingArchitect!=null){
                        existingArchitect.setFirstName(
                            userUpdateRequest.getFirstName());
                        existingArchitect.setLastName(userUpdateRequest.getLastName());
                        existingArchitect.setBusinessContactNo(
                            userUpdateRequest.getBusinessContactNo());
                        existingArchitect.setBusinessName(
                            userUpdateRequest.getBusinessName());
                        existingArchitect.setAddressLine1(
                            userUpdateRequest.getAddressLine1());
                        existingArchitect.setAddressLine2(
                            userUpdateRequest.getAddressLine2());
                        existingArchitect.setCity(userUpdateRequest.getCity());
                        existingArchitect.setDistrict(
                            userUpdateRequest.getDistrict());
                        existingArchitect.setMinRate(
                            userUpdateRequest.getMinRate());
                        existingArchitect.setMaxRate(
                            userUpdateRequest.getMaxRate());
                        if (profileImageName != null) {
                            existingArchitect.setProfileImage(profileImageName);
                            existingArchitect.setProfileImageName(
                                FileUploadUtil.generateFileName(profileImageName));
                        }
                    }
                    savedUser=architectService.saveArchitect(existingArchitect);
                }
                if (savedUser != null) {
                    if (userUpdateRequest.getSpecialization() != null &&
                        !userUpdateRequest.getSpecialization().isBlank()) {
                        specializations = Arrays.stream(
                                                    userUpdateRequest.getSpecialization().split(","))
                                                .map(String::trim)
                                                .collect(Collectors.toList());
                        User finalSavedUSer = savedUser;
                        specializations.forEach(specialization -> {
                            userDetailsUtil.saveSpecialization(finalSavedUSer,
                                specialization);
                        });
                    }
                    if (userUpdateRequest.getQualification() != null &&
                        !userUpdateRequest.getQualification().isBlank()) {
                        qualifications = Arrays.stream(
                                                   userUpdateRequest.getQualification().split(",")).map(String::trim)
                                               .collect(Collectors.toList());
                        User finalSavedUser = savedUser;
                        qualifications.forEach(qualification -> {
                            userDetailsUtil.saveQualification(finalSavedUser,
                                qualification);
                        });
                    }
                    if (userUpdateRequest.getServiceAreas() != null) {
                        User finalSavedUser = savedUser;
                        userUpdateRequest.getServiceAreas().forEach(serviceArea -> {
                            userDetailsUtil.saveServiceArea(finalSavedUser,
                                serviceArea);
                        });
                    }
                    if (savedUser.getProfileImageName() != null) {
                        String uploadDir = "./files/profile-images/" + savedUser.getId();
                        try {
                            FileUploadUtil.saveFile(uploadDir,
                                userUpdateRequest.getProfileImage(),
                                savedUser.getProfileImageName());
                        } catch (IOException e) {
                            System.out.println("Error saving file");
                        }
                    }
                    response.setId(savedUser.getId().longValue());
                    response.setSuccess(true);
                } else {
                    response.setSuccess(false);
                    response.setMessage(
                        "Something went wrong please try again");
                }
            }

        }
        return response;
    }

    @Override
    public ResponseEntity<User> verifyUser(Integer id) {
        Optional<User> optionalUser=userRepository.findById(id);

        if (optionalUser.isPresent()){
            User existedUser=optionalUser.get();
            existedUser.setVerified(true);
            User savedUser=userRepository.save(existedUser);
            return ResponseEntity.ok(savedUser);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<User> activeOrSuspendAcccount(Integer id,
        AccountStatus action) {
        Optional<User> optionalUser=userRepository.findById(id);
        if (optionalUser.isPresent()){
            User existedUser=optionalUser.get();
            existedUser.setStatus(action);
            User savedUser=userRepository.save(existedUser);
            return  ResponseEntity.ok(savedUser);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }


    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
