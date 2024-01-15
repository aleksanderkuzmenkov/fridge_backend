package com.zephir.fridgeapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class UserRegistrationSecurityConfig {

    private static final String[] UN_SECURED_URLs = {
            "/register/**",
            "/users/**",
            "/fridges/**",
            "/verification-token/**"

    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(UN_SECURED_URLs).permitAll()
                        .anyRequest().hasAnyAuthority("USER", "ADMIN")
                )
                .formLogin((form) -> form
                        .loginPage("/users")
                        .permitAll()
                );

        return http.build();
    }
}

