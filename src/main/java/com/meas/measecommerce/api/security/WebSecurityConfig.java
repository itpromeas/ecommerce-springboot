package com.meas.measecommerce.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Application security
 */
@Configuration
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/product",
            "/auth/register",
            "/auth/login",
            "/auth/verify",
            "/auth/forgot",
            "/auth/reset",
            "/error",
            "/websocket",
            "/websocket/**"
    };

    private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Filter chain to configure security.
     * @param http The security object.
     * @return The chain built.
     * @throws Exception Thrown on error configuring.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable()).cors(cors->cors.disable());
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);

        http.authorizeHttpRequests(
                auth->auth
                        // Specific exclusions or rules.
                        .requestMatchers("/product", "/auth/register", "/auth/login",
                                "/auth/verify", "/auth/forgot", "/auth/reset", "/error",
                                "/websocket", "/websocket/**").permitAll()
                        // Everything else should be authenticated.
                        .anyRequest().authenticated()
        );

        return http.build();
    }
}
