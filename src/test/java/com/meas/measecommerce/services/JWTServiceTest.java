package com.meas.measecommerce.services;

import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Class to test the JWTService.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class JWTServiceTest {

    /** The JWTService to test. */
    @Autowired
    private JWTService jwtService;
    /** The Local User DAO. */
    @Autowired
    private UserDAO localUserDAO;

    /**
     * Tests that the verification token is not usable for login.
     */
    @Test
    public void testVerificationTokenNotUsableForLogin() {
        User user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJWT(user);
        Assertions.assertNull(jwtService.getUsername(token), "Verification token should not contain username.");
    }

    /**
     * Tests that the authentication token generate still returns the username.
     */
    @Test
    public void testAuthTokenReturnsUsername() {
        User user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJWT(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUsername(token), "Token for auth should contain users username.");
    }

}
