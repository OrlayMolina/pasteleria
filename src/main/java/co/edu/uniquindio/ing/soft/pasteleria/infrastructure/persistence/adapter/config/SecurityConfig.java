package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2SuccessHandler successHandler;
    private final OAuth2FailureHandler failureHandler;
    private final TokenFilter tokenFilter;

    public SecurityConfig(OAuth2SuccessHandler successHandler, OAuth2FailureHandler failureHandler, TokenFilter tokenFilter) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.tokenFilter = tokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:4200", "https://pasteleria-feliz.web.app")); // solo uno
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("Origin", "Accept", "Content-Type", "Authorization"));
                            config.setAllowCredentials(true);
                            return config;
                        })
                )
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/auth/login")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler) // aquí se configura
                )
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class); // <-- Aquí se integra

        return http.build();
    }
}
