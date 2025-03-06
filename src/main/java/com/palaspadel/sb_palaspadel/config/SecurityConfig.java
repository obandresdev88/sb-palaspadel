package com.palaspadel.sb_palaspadel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 🔥 Permitir todo temporalmente
                )
                .formLogin(login -> login.disable())  // 🔥 Deshabilitar login
                .logout(logout -> logout.disable());  // 🔥 Deshabilitar logout

        return http.build();
    }
}
