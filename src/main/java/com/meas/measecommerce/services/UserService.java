package com.meas.measecommerce.services;

import com.meas.measecommerce.api.dtos.LoginBody;
import com.meas.measecommerce.api.dtos.RegistrationBody;
import com.meas.measecommerce.exceptions.UserAlreadyExistsException;
import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.dao.UserDAO;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling user actions.
 */
@Service
public class UserService {

    /** The UserDAO. */
    private UserDAO userDAO;

    private EncryptionService encryptionService;

    private JWTService jwtService;

    /**
     * Constructor injected by spring.
     *
     * @param userDAO
     * @param encryptionService
     * @param jwtService
     */
    public UserService(UserDAO userDAO, EncryptionService encryptionService, JWTService jwtService) {
        this.userDAO = userDAO;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    /**
     * Attempts to register a user given the information provided.
     * @param registrationBody The registration information.
     * @return The user that has been written to the database.
     * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
     */
    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if(userDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
                || userDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()){
            throw  new UserAlreadyExistsException();
        }
        User user = new User();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        // encrypt password
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));

        return userDAO.save(user);
    }


    /**
     * Logins in a user and provides an authentication token back.
     * @param loginBody The login request.
     * @return The authentication token. Null if the request was invalid.
     */
    public String loginUser(LoginBody loginBody) {
        Optional<User> opUser = userDAO.findByUsernameIgnoreCase(loginBody.getUsername());
        if (opUser.isPresent()) {
            User user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }
        return null;
    }


}
