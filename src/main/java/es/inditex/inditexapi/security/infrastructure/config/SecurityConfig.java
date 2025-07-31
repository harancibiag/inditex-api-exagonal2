package es.inditex.inditexapi.security.infrastructure.config;

import es.inditex.inditexapi.security.application.port.in.SecurityUseCase;
import es.inditex.inditexapi.security.infrastructure.jwt.JwtTokenValidator;
import es.inditex.inditexapi.security.infrastructure.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    @Profile("!prod")
    public SecurityFilterChain securityFilterChainDev(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // Permitir H2 console
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(PathRequest.toH2Console()).permitAll();
                    http.requestMatchers("/auth/**").permitAll();
                    http.requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    http.anyRequest().authenticated();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(SecurityUseCase securityUseCase, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder); // Usa el bean de PasswordEncoder
        provider.setUserDetailsService(securityUseCase); // Usa nuestro caso de uso para buscar usuarios
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}