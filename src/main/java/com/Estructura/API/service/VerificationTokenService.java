package com.Estructura.API.service;

import com.Estructura.API.model.TokenType;
import com.Estructura.API.model.User;
import com.Estructura.API.model.VerificationToken;

import java.util.Optional;


public interface VerificationTokenService {
    void saveVerificationToken(User user, String verificationToken,
        TokenType tokenType);

    VerificationToken findByToken(String token);

    String validateVerificationToken(String theToken);

    Optional<User> findUserByPasswordRestToken(String passwordRestToken);

    VerificationToken generateNewVerificationToken(String oldToken);
}
