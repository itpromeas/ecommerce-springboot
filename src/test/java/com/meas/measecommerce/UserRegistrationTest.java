package com.meas.measecommerce;

import com.meas.measecommerce.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class UserRegistrationTest {

    User user;

    @BeforeEach
    void setUp(){
        user = new User();
    }

    @Test
    void emailIsNotNull(){
        /*user.setEmail("");
        user.setLastName("test");
        user.setFirstName("testF");
        user.setLastName("testL");
        user.setPassword("Qwertz1%");*/
        // TODO
    }

    @Test
    void emailHasCorrectPattern(){
        // TODO
    }

    @Test
    void usernameIsNotNull(){
        // TODO
    }

    @Test
    void usernameSizeIsNotLessThan4(){
        // TODO
    }
}
