package com.gang.ConfigSequrity;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management ->
                management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize ->
                authorize
                    .requestMatchers("/auth/signup","/auth/signin").permitAll()
                    .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_RESTAURANT_OWNER", "ROLE_ADMIN") // Use hasAnyAuthority
                    .requestMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(configurationSource()));

        return http.build();
    }

    private CorsConfigurationSource configurationSource() {
        return request -> {
            CorsConfiguration crg = new CorsConfiguration();
            crg.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Update with your actual frontend URL
            crg.setAllowedMethods(Collections.singletonList("*"));
            crg.setAllowCredentials(true);
            crg.setAllowedHeaders(Collections.singletonList("*"));
            crg.setExposedHeaders(Arrays.asList("authorization"));
            crg.setMaxAge(3600L);
            return crg;
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
