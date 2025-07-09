package tn.iit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // D�sactivation de la protection CSRF
            .authorizeHttpRequests(auth -> auth
             .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/projetcompain/v3/api-docs",
                    "/webjars/**"
                ).permitAll()
                .anyRequest().permitAll() // Autoriser toutes les requ�tes sans authentification
            );

        return http.build();
    }
}
