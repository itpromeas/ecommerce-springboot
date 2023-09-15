package com.meas.measecommerce.services;

import com.meas.measecommerce.exceptions.EmailFailureException;
import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    /** The from address to use on emails. */
    @Value("${email.from}")
    private String fromAddress;

    /** The url of the front end for links. */
    @Value("${app.frontend.url}")
    private String url;

    /** The JavaMailSender instance. */
    private JavaMailSender javaMailSender;

    /**
     * Constructor for spring injection.
     * @param javaMailSender
     */
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Sends a verification email to the user.
     * @param verificationToken The verification token to be sent.
     * @throws EmailFailureException Thrown if are unable to send the email.
     */
    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(verificationToken.getUser().getEmail());

            message.setSubject("Please verify your email to activate your account.");
            String msgString = "You can follow the link below to verify your email and active your account.\n\n" +
                    url + "/auth/verify?token=" + verificationToken.getToken();

            message.setText(msgString);

            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }

    /**
     * Sends a password reset request email to the user.
     * @param user The user to send to.
     * @param token The token to send the user for reset.
     * @throws EmailFailureException
     */
    public void sendPasswordResetEmail(User user, String token) throws EmailFailureException {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());

            message.setSubject("Your password reset request link.");
            String msgString = "You requested a password reset on our website. Please " +
                    "find the link below to be able to reset your password.\n" + url +
                    "/auth/reset?token=" + token;

            message.setText(msgString);

            javaMailSender.send(message);
        } catch (MailException ex) {
            throw new EmailFailureException();
        }
    }


}
