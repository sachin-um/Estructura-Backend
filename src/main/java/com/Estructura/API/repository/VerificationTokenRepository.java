package com.Estructura.API.repository;

import com.Estructura.API.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    VerificationToken findByToken(String token);
}
