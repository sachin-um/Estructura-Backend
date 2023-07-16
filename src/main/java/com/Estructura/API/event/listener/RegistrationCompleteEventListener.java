package com.Estructura.API.event.listener;


import com.Estructura.API.email.EmailService;
import com.Estructura.API.event.RegistrationCompleteEvent;
import com.Estructura.API.model.User;
import com.Estructura.API.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static com.Estructura.API.model.TokenType.EMAIL_VERIFICATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final EmailService emailService;
    private User theUser;
    private final VerificationTokenService verificationTokenService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        theUser=event.getUser();
        String verificationToken= UUID.randomUUID().toString();
        verificationTokenService.saveVerificationToken(theUser,verificationToken,EMAIL_VERIFICATION);
        String url=event.getApplicationUrl()+"/api/v1/auth/verifyEmail?token="+verificationToken;
        try {
            sendEmailVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your Email : {}",url);
    }
    public void sendEmailVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Estructura Email Verification";
        String senderName = "Team Estructura";
        String mailContent = "<p> Hi, "+ theUser.getFirstname()+ ", </p>"+
                "<p>Thank you for registering with Estructura,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Team Estructura";
        String receiver=theUser.getEmail();
        emailService.send(receiver,mailContent,subject,senderName);

    }
}
