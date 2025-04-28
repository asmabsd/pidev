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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/tourisme/**",
                                "/tourisme/plate/addPlate",
                                "/tourisme/plate/getPlatesByMenu",
                                "/tourisme/menu/addMenu",
                                "/plate/addPlate",
                                "/gastronomy/addGastronomy",
                                "/gastronomy/updateGastronomy",
                                "/tourisme/gastronomy/updateGastronomy",
                                "/gastronomy/deleteGastronomy",
                                "/tourisme/gastronomy/deleteGastronomy",
                                "/plate/updatePlate",
                                "/tourisme/plate/deletePlate",
                                "/tourisme/detailGastronomy/addDetailGastronomy",
                                "/gastronomy/addDetailGastronomyAndAffectGastronomy",
                                "/tourisme/menu/getMenusByGastronomy/**",
                                "/tourisme/menu/getMenusByGastronomy/",
                                "/tourisme/gastronomy/search",
                                "/gastronomy/search",
                                "/menu/getMenusByGastronomy/",
                                "/gastronomy/affectMenuToGastronomy/",
                                "/auth/login",
                                "/auth/**",
                                "/oauth2/**",
                                "/login/**",
                                "/menu/addMenu",
                                "/users/views/**",
                                "/complete-profile",
                                "/complete-profile/**",
                                "/api/users/**",
                                "/Guide/addGuide/**",
                                "/gastronomy/addGastronomy",
                                "/souvenir/addSouvenir/**",
                                "/activity/**",
                                "/gastronomy/**",  // Simplify by allowing all gastronomy endpoints
                                "/menu/**",       // Simplify by allowing all menu endpoints
                                "/detailGastronomy/**",
                                "/tourisme/gastronomy/addGastronomy",
                                "/images/**",
                                "/tourisme/images/**",
                                "/tourisme/activity/**",
                                "/tourisme/gastronomy/rating/**",
                                "/gastronomy/rating/**",
                                "/gastronomy/rating",
                                "/tourisme/gastronomy/rating",
                                "/tourisme/gastronomy/generateAdvice",
                                "/gastronomy/generateAdvice",
                                "/gastronomy/generateAdvice/**",

                                "/tourisme/activity/addactivity",
                                "/tourisme/api/statistics/**",
                                "/tourisme/api/statistics/",
                                "/tourisme/api/predict",
                                "/api/statistics/**",
                                "/api/statistics",
                                "/api/predict",
                                "/api/predict/**",
                                "/tourisme/api/statistics/average-rating-by-location",
                                "/api/statistics/average-rating-by-location",

                                "/plate/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
               /* .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureUrl("/login?error=oauth_error")
                )*/
                .authenticationProvider(authenticationProvider) // ✅ Move this AFTER .oauth2Login()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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