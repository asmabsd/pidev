package com.example.pidev.config;

import com.example.pidev.service.User.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests


                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/Guide/deleteGuide/").permitAll()
                        .requestMatchers("/api/**").permitAll() // Allow access to all API endpoints
                        .requestMatchers("/Guide/**").permitAll()
                        .requestMatchers("/souvenir/**").permitAll()

                        .requestMatchers("/store/**").permitAll()
                        .requestMatchers("/store/**/**  ").permitAll()

                        .requestMatchers("/panel/**").permitAll()
                        .requestMatchers("/ReservationGuide/addReservationGuide").permitAll()
                        .requestMatchers("/ReservationGuide/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/Guide/updateGuide/**").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.DELETE, "/api/users/").permitAll() // Adjust access
                        ///api/users/delete/{{id}}

                        .requestMatchers(HttpMethod.POST, "/Guide/**/uploadImage").permitAll()
                        .requestMatchers("/Guide/**/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Guide/**/image").permitAll()
                        .requestMatchers("/Guide/updateGuide/**").permitAll()
                        .requestMatchers("/Guide//uploads/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/updateReservationGuide/**").permitAll() // Adjust access
                        .anyRequest().authenticated()  // Require authentication for all other routes
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                        .loginPage("http://localhost:4200/login") // Redirection vers la page de login Angular après le succès de l'authentification
                )
                .authenticationProvider(authenticationProvider) // Move this AFTER .oauth2Login()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));  // Allow specific origins // Autoriser ton app Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Autoriser ces méthodes
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // Autoriser ces headers nécessaires
        configuration.setAllowCredentials(true); // Autoriser les credentials si besoin
        configuration.setExposedHeaders(List.of("Authorization")); // Exposer ce header pour le token JWT
        configuration.setAllowCredentials(true); // Important pour OAuth2 et JWT

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Appliquer cette configuration CORS à toutes les routes

        return source;
    }
}
