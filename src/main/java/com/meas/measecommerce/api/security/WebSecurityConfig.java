package com.meas.measecommerce.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Application security
 */
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //TODO: Proper authentication
        http.csrf(csrf->csrf.disable()).cors(cors->cors.disable());
        http.authorizeHttpRequests(auth->auth.anyRequest().permitAll());
        return http.build();
    }
}
