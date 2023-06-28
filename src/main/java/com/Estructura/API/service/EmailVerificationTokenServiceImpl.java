package com.Estructura.API.service;

import com.Estructura.API.model.EmailVerificationToken;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.EmailVerificationTokenRepository;
import com.Estructura.API.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@AllArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService{
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void saveEmailVerificationTokenService(User user, String verificationToken) {
        var emailVerificationToken=new EmailVerificationToken(verificationToken,user);
        emailVerificationTokenRepository.save(emailVerificationToken);
    }

    @Override
    public EmailVerificationToken findByToken(String token) {
        return emailVerificationTokenRepository.findByToken(token);
    }

    @Override
    public String validateToken(String theToken) {
        EmailVerificationToken token=emailVerificationTokenRepository.findByToken(theToken);
        if (token==null){
            return "Invalid Token";
        }
        User user=token.getUser();
        Calendar calendar=Calendar.getInstance();
        if ((token.getExpirationTime().getTime()-calendar.getTime().getTime()) <= 0){
            emailVerificationTokenRepository.delete(token);
            return "Token already expired";
        }
        user.setVerified(true);
        userRepository.save(user);
        return "valid";
    }
}
