package com.example.pidev.config;

import com.example.pidev.service.User.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .authorizeHttpRequests(auth -> auth
                        // Permet l'accès à certaines URL sans authentification
                        .requestMatchers(
                                "/tourisme/**",
                                "/tourisme/login",
                                "/auth/**",
                                "/oauth2/**",
                                "/login/**", // Retirer les pages de login si nécessaire
                                "/users/views/**",
                                "/complete-profile",
                                "/api/users/**",
                                "/Guide/addGuide/**",
                                "/gastronomy/addGastronomy/**",
                                "/souvenir/addSouvenir/**",
                                "/activity/**",
                                "/hebergement/addhebergement/**",
                                "/hebergement/getallh/**",
                                "/reservationchambre/addreservationchambre/**",
                                "/hebergement/getoneh/**",
                                "/hebergement/ajouterReservation/**",
                                "/tourisme/hebergement/addhebergement/**",
                                "/hebergement/modifyhebergement/**",
                                "/hebergement/removehebergement/**",
                                "/hebergement/getoneh/**",
                                "/reservationchambre/getonereservationchambre/**",
                                "/reservationchambre/getallr/**",
                                "/hebergement/reservations/**",
                                "/reservationchambre/removereservationchambre/**",
                                "/reservationchambre/modifyreservationchambre/**"
                                ).permitAll() // Autoriser l'accès sans authentification
                        .anyRequest().authenticated()  // Toutes les autres requêtes nécessitent une authentification
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")  // Configure une page de connexion personnalisée
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureUrl("/login?error=oauth_error")  // Page en cas d'erreur d'authentification
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Utilisation de filtres d'authentification personnalisés

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Allow your Angular app
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow these methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // Allow necessary headers
        configuration.setAllowCredentials(true); // Allow credentials if needed
        configuration.setExposedHeaders(List.of("Authorization")); // Add this to expose JWT token
        configuration.setAllowCredentials(true); // Important for OAuth2 and JWT

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this CORS configuration to all routes

        return source;
    }}