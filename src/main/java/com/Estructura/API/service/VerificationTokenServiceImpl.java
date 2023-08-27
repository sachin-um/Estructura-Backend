package com.Estructura.API.service;

import com.Estructura.API.exception.TokenNotFoundException;
import com.Estructura.API.model.TokenType;
import com.Estructura.API.model.User;
import com.Estructura.API.model.VerificationToken;
import com.Estructura.API.repository.UserRepository;
import com.Estructura.API.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import static com.Estructura.API.model.TokenType.EMAIL_VERIFICATION;

@Service
@AllArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void saveVerificationToken(User user, String verificationToken,
        TokenType tokenType) {
        var token = new VerificationToken(verificationToken, user, tokenType);
        verificationTokenRepository.save(token);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public String validateVerificationToken(String theToken) {
        VerificationToken token = verificationTokenRepository.findByToken(
            theToken);
        if (token == null) {
            return "Invalid Token";
        }
        User     user     = token.getUser();
        Calendar calendar = Calendar.getInstance();
        System.out.println(token.getToken());
        if ((
                token.getExpirationTime().getTime() -
                calendar.getTime().getTime()
            ) <= 0) {
            return "Expired";
        } else {
            if (token.getTokenType() == EMAIL_VERIFICATION) {
                user.setVerified(true);
                userRepository.save(user);
            }
            return "valid";
        }
    }


    @Override
    public Optional<User> findUserByPasswordRestToken(
        String passwordRestToken) {
        return Optional.ofNullable(
            verificationTokenRepository.findByToken(passwordRestToken)
                                       .getUser());
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken =
            verificationTokenRepository.findByToken(
                oldToken);// if not available
        if (verificationToken.getToken() == null) {
            throw new TokenNotFoundException("Token not found");
        } else {
            var verificationTokenTime =
                new VerificationToken().getTokenExpirationTime();
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationToken.setExpirationTime(verificationTokenTime);
            return verificationTokenRepository.save(verificationToken);
        }
    }
}
