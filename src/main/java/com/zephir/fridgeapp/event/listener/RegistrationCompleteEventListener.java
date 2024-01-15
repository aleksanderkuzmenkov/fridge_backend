package com.zephir.fridgeapp.event.listener;

import com.zephir.fridgeapp.event.RegistrationCompleteEvent;
import com.zephir.fridgeapp.registration.token.VerificationToken;
import com.zephir.fridgeapp.registration.token.VerificationTokenRepository;
import com.zephir.fridgeapp.user.User;
import com.zephir.fridgeapp.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final UserService userService;
    private final JavaMailSender mailSender;
    private final VerificationTokenRepository verificationTokenRepository;
    private User user;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        // Get registered user
        user = event.getUser();

        // Create verification token for new user
        String verificationToken = UUID.randomUUID().toString();

        VerificationToken existingToken = verificationTokenRepository.findByUser_Id(user.getId());

        if (existingToken != null) {
            existingToken.setToken(verificationToken);
            existingToken.setExpirationTime(existingToken.getTokenExpirationTime());

            verificationTokenRepository.save(existingToken);
            verificationToken = existingToken.getToken();
        } else {
            VerificationToken newVerificationToken = new VerificationToken(verificationToken, user);

            verificationToken = newVerificationToken.getToken();

            // Save verification token of new user
            userService.saveUserVerificetionToken(user, newVerificationToken.getToken());
        }

        // Build verification url to send to user
        String url = event.getApplicationUrl() + "/register/verifyEmail?token="+verificationToken;

        // Send email to user
        try {
            sendVerificationEmail(url);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify : {}", url);

    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, "+ user.getName()+ ", </p>"+
                "<p>Thank you for registering with us,"+"" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("aleksand.kuz@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
