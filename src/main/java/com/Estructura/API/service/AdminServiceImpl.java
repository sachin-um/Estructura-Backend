package com.Estructura.API.service;

import com.Estructura.API.exception.UserAlreadyExistsException;
import com.Estructura.API.model.Admin;
import com.Estructura.API.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

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
}
