package com.Estructura.API.service;

import com.Estructura.API.model.EmailVerificationToken;
import com.Estructura.API.model.User;


public interface EmailVerificationTokenService {
    public void saveEmailVerificationTokenService(User user,String verificationToken);

    EmailVerificationToken findByToken(String token);

    String validateToken(String theToken);

    EmailVerificationToken generateNewVerificationToken(String oldToken);
}
