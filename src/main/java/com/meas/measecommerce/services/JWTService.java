package com.meas.measecommerce.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.meas.measecommerce.models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for handling JWTs for user authentication.
 * Json Web Token (JWT)
 * https://jwt.io/libraries for more information about jwt libraries
 */
@Service
public class JWTService {
    /** The secret key to encrypt the JWTs with. */
    @Value("${jwt.algorithm.key}") // Do not forget to define jwt.algorithm.key in application.properties
    private String algorithmKey;

    /** The issuer the JWT is signed with. */
    @Value("${jwt.issuer}") // Do not forget to define jwt.issuer in application.properties
    private String issuer;

    /** How many seconds from generation should the JWT expire? */
    @Value("${jwt.expiryInSeconds}") // Do not forget to define jwt.expiryInSeconds in application.properties
    private int expiryInSeconds;

    /** The algorithm generated post construction. */
    private Algorithm algorithm;

    /** The JWT claim key for the username. */
    private static final String USERNAME_KEY = "USERNAME";
    private static final String EMAIL_KEY = "EMAIL";

    /**
     * Post construction method.
     */
    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    /**
     * Generates a JWT based on the given user.
     * @param user The user to generate for.
     * @return The JWT.
     */
    public String generateJWT(User user) {
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    /**
     * Generates a special token for verification of an email.
     * @param user The user to create the token for.
     * @return The token generated.
     */
    public String generateVerificationJWT(User user){
        return JWT.create()
                .withClaim(EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String getUsername(String token){
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }
}
