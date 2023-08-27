package com.meas.measecommerce.api.controllers.auth;

import com.meas.measecommerce.api.dtos.LoginBody;
import com.meas.measecommerce.api.dtos.LoginResponse;
import com.meas.measecommerce.api.dtos.RegistrationBody;
import com.meas.measecommerce.exceptions.UserAlreadyExistsException;
import com.meas.measecommerce.models.User;
import com.meas.measecommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registrerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     *
     * @param loginBody
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = userService.loginUser(loginBody);
        if(jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public User getLoggedUserProfile(@AuthenticationPrincipal User user){
        return user;
    }
}
