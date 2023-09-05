package com.meas.measecommerce.services;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetup;
import com.meas.measecommerce.api.dtos.LoginBody;
import com.meas.measecommerce.api.dtos.RegistrationBody;
import com.meas.measecommerce.exceptions.EmailFailureException;
import com.meas.measecommerce.exceptions.UserAlreadyExistsException;
import com.meas.measecommerce.exceptions.UserNotVerifiedException;
import com.meas.measecommerce.models.VerificationToken;
import com.meas.measecommerce.models.dao.VerificationTokenDAO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    /** Extension for mocking email sending. */
    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetup.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true); // this will wipe the inbox everytime

    /** The UserService to test. */
    @Autowired
    private UserService userService;

    /** The Verification Token DAO. */
    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Test
    @Transactional
    public void testRegisterUser(){
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword123");

        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body),
                "Username already exists."
        );

        body.setUsername("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");

        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body),
                "Email already exists."
        );

        body.setEmail("UserServiceTest$testRegisterUser@junit.com");

        Assertions.assertDoesNotThrow(
                () -> userService.registerUser(body),
                "User should register successfully."
        );

        try {
            Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                    .getRecipients(Message.RecipientType.TO)[0].toString());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Tests the loginUser method.
     * @throws UserNotVerifiedException
     * @throws EmailFailureException
     */
    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        LoginBody body = new LoginBody();
        body.setUsername("UserA-NotExists");
        body.setPassword("PasswordA123-BadPassword");
        Assertions.assertNull(userService.loginUser(body), "The user should not exist.");
        body.setUsername("UserA");
        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");
        body.setPassword("PasswordA123");
        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");
        body.setUsername("UserB");
        body.setPassword("PasswordB123");

        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should be sent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be resent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
    }

    /**
     * Tests the verifyUser method.
     * @throws EmailFailureException
     */
    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist should return false.");
        LoginBody body = new LoginBody();
        body.setUsername("UserB");
        body.setPassword("PasswordB123");
        try {
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            List<VerificationToken> tokens = verificationTokenDAO.findByUser_IdOrderByIdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token), "Token should be valid.");
            Assertions.assertNotNull(body, "The user should now be verified.");
        }
    }
}
