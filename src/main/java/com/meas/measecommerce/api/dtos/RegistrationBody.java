package com.meas.measecommerce.api.dtos;

import jakarta.validation.constraints.*;

public class RegistrationBody {
    /** The username. */
    @NotNull
    @NotBlank
    @Size(min=4,max=255)
    private String username;
    /** The email. */

    @NotNull
    @NotBlank
    @Email
    private String email;

    /** The password. */
    /*
    eight characters, at least one letter and one number: "^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$";
    Minimum eight characters, at least one letter, one number and one special character: "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
    Minimum eight characters, at least one uppercase letter, one lowercase letter and one number: "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"
    Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character: "^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"

    This regex will enforce these rules: "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    -At least one upper case English letter, (?=.*?[A-Z])
    -At least one lower case English letter, (?=.*?[a-z])
    -At least one digit, (?=.*?[0-9])
    -At least one special character, (?=.*?[#?!@$%^&*-])
    -Minimum eight in length .{8,} (with the anchors)
    */
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$")
    @Size(min=6, max=32)
    private String password;

    /** The first name. */
    @NotNull
    @NotBlank
    private String firstName;

    /** The last name. */
    @NotNull
    @NotBlank
    private String lastName;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
