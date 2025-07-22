package com.example.budgetservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    // for HS256 (shared secret)
                    .decoder(NimbusJwtDecoder.withSecretKey(
                            new SecretKeySpec(
                                    Base64.getDecoder().decode("${jwt.secret}"),
                                    "HmacSHA256"
                            )).build()
                    )
                    // OR, for RS256 (public key/JWK):
                    // .jwkSetUri("https://auth.example.com/.well-known/jwks.json")
                )
            );
        return http.build();
    }
}
