package com.meas.measecommerce.services;

import com.meas.measecommerce.api.dtos.RegistrationBody;
import com.meas.measecommerce.exceptions.UserAlreadyExistsException;
import com.meas.measecommerce.models.User;
import com.meas.measecommerce.models.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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

        // TODO: encrypt password
        user.setPassword(registrationBody.getPassword());

        return userDAO.save(user);
    }
}
