package com.Estructura.API.event.listener;

import com.Estructura.API.email.EmailService;
import com.Estructura.API.event.PasswordResetEvent;
import com.Estructura.API.model.User;
import com.Estructura.API.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static com.Estructura.API.model.TokenType.RESET_PASSWORD;

@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordResetEventListener implements
    ApplicationListener<PasswordResetEvent> {

    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private User theUser;

    @Override
    public void onApplicationEvent(PasswordResetEvent event) {
        theUser = event.getUser();
        String passwordRestToken = UUID.randomUUID().toString();
        verificationTokenService.saveVerificationToken(theUser,
                                                       passwordRestToken,
                                                       RESET_PASSWORD
        );
        String url =
            event.getApplicationUrl() + "/api/v1/auth/reset-password?token=" +
            passwordRestToken;
        try {
            sendPasswordRestEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your Email : {}", url);
    }

    private void sendPasswordRestEmail(
        String url) throws MessagingException, UnsupportedEncodingException {
        String subject    = "Estructura Reset Password";
        String senderName = "Team Estructura";
        String mailContent = "<p> Hi, " + theUser.getFirstName() + ", </p>" +
                             "<p>You recently requested to reset your " +
                             "password," +
                             "Please, follow the link below to complete the " +
                             "action.</p>" +
                             "<a href=\"" + url + "\">Rest password</a>" +
                             "<p> Thank you <br> Team Estructura";
        String receiver = theUser.getEmail();
        emailService.send(receiver, mailContent, subject, senderName);
    }
}
