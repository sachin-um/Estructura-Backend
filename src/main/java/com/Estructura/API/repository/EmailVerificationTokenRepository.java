package com.Estructura.API.repository;

import com.Estructura.API.model.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken,Integer> {
    EmailVerificationToken findByToken(String token);
}
