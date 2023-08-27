package com.meas.measecommerce.models.dao;

import com.meas.measecommerce.models.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserDAO extends ListCrudRepository<User, Long> {
    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByEmailIgnoreCase(String email);

}
