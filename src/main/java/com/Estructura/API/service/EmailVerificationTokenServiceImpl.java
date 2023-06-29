package com.Estructura.API.service;

import com.Estructura.API.exception.TokenNotFoundException;
import com.Estructura.API.model.EmailVerificationToken;
import com.Estructura.API.model.User;
import com.Estructura.API.repository.EmailVerificationTokenRepository;
import com.Estructura.API.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

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
        System.out.println(token.getToken());
        if ((token.getExpirationTime().getTime() -calendar.getTime().getTime()) <= 0){
            return "Token already expired";
        }
        user.setVerified(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public EmailVerificationToken generateNewVerificationToken(String oldToken) {
        EmailVerificationToken verificationToken=emailVerificationTokenRepository.findByToken(oldToken);// if not available
        if (verificationToken.getToken()==null){
            throw new TokenNotFoundException("Token not found");
        }
        else {
            var verificationTokenTime=new EmailVerificationToken().getTokenExpirationTime();
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationToken.setExpirationTime(verificationTokenTime);
            return emailVerificationTokenRepository.save(verificationToken);
        }
    }
}
