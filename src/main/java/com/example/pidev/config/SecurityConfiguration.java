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
                        .requestMatchers("/Planning/addPlanning").permitAll()
                        .requestMatchers("/Planning/viewPlanning").permitAll()
                        .requestMatchers("/Guide/images/**").permitAll()
                        .requestMatchers("/ReservationGuide/viewReservationGuide").permitAll()
                        .requestMatchers("/api/admin/pending-users").permitAll()

                                 //
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
                                        "/souvenir/addSouvenir/**",
                                        "/activity/**",
                                        "/gastronomy/**",  // allow all gastronomy endpoints
                                        "/menu/**",       // allow all menu endpoints
                                        "/detailGastronomy/**",
                                        "/tourisme/gastronomy/addGastronomy",
                                        "/images/**",
                                        "/tourisme/images/**",
                                        "/tourisme/activity/**",
                                        "/tourisme/activity/addactivity",
                                        "/tourisme/api/statistics/**",
                                        "/tourisme/api/statistics/",
                                        "/api/statistics/**",
                                        "/api/statistics",
                                        "/plate/**",
                                        "/hebergement/addhebergement/**",
                                        "/hebergement/getallh/**",
                                        "/reservationchambre/addreservationchambre/**",
                                        "/hebergement/getoneh/**",
                                        "/hebergement/ajouterReservation/**",
                                        "/hebergement/modifyhebergement/**",
                                        "/hebergement/removehebergement/**",
                                        "/hebergement/getoneh/**",
                                        "/reservationchambre/getonereservationchambre/**",
                                        "/reservationchambre/getallr/**",
                                        "/hebergement/reservations/**",
                                        "/reservationchambre/removereservationchambre/**",
                                        "/reservationchambre/modifyreservationchambre/**",
                                        "/hebergement/rate/**",
                                        "/activity/*/like",
                                        "/activity/*/dislike",
                                        "/activity/*/reaction",
                                        "/blog/**",
                                        "/reservation/getAllReservations",
                                        "/reservation/user/**",
                                        "/reservation/**"
                        ///
                                ).permitAll()
                               //



                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/Planning/addPlanning").permitAll()
                        .requestMatchers("/Planning/viewPlanning").permitAll()
                        .requestMatchers("/Guide/images/**").permitAll()
                        .requestMatchers("/ReservationGuide/viewReservationGuide").permitAll()
                        .requestMatchers("/api/admin/pending-users").permitAll()

                        //
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
                                "/souvenir/addSouvenir/**",
                                "/activity/**",
                                "/gastronomy/**",  // allow all gastronomy endpoints
                                "/menu/**",       // allow all menu endpoints
                                "/detailGastronomy/**",
                                "/tourisme/gastronomy/addGastronomy",
                                "/images/**",
                                "/tourisme/images/**",
                                "/tourisme/activity/**",
                                "/tourisme/activity/addactivity",
                                "/tourisme/api/statistics/**",
                                "/tourisme/api/statistics/",
                                "/api/statistics/**",
                                "/api/statistics",
                                "/ReservationGuide/addReservationGuide",
                                "/plate/**"
                        ).permitAll()
                        //



                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/user-id/**").permitAll()
                        .requestMatchers("/Planning/getByguide/**").permitAll()
                        .requestMatchers("/chat/**").permitAll()
                        .requestMatchers("/Guide/contact/**").permitAll()


                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/ReservationGuide/translate").permitAll()
                        .requestMatchers("/api/statistics/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/api/users/getemail/**").permitAll()
                        .requestMatchers("/Guide/deleteGuide/").permitAll()
                        .requestMatchers("/api/**").permitAll() // Allow access to all API endpoints
                        .requestMatchers("/Guide/**").permitAll()
                        .requestMatchers("/ReservationGuide/addReservationGuide").permitAll()
                        .requestMatchers("/ReservationGuide/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/Guide/updateGuide/**").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.DELETE, "/Guide/deleteGuide/").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.POST, "/Guide/**/uploadImage").permitAll()
                        .requestMatchers("/Guide/**/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Guide/**/image").permitAll()

                        .requestMatchers("/Guide//uploads/**").permitAll()
                        .requestMatchers("/email/**").permitAll()

                        .requestMatchers("/Planning/check-reservation").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/**/status").permitAll() // Adjust access

                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/updateReservationGuide/**").permitAll() // Adjust access
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/profile/complete/").permitAll()
                        .requestMatchers("/Guide/deleteGuide/").permitAll()
                        .requestMatchers("/api/**").permitAll() // Allow access to all API endpoints
                        .requestMatchers("/Guide/**").permitAll()
                        .requestMatchers("/ReservationGuide/addReservationGuide").permitAll()
                        .requestMatchers("/ReservationGuide/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/Guide/updateGuide/**").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.DELETE, "/api/users/").permitAll() // Adjust access
                        ///api/users/delete/{{id}}
                        .requestMatchers("/Guide/uploads/**").permitAll()
                        .requestMatchers("/email/sendemail").permitAll()
                        .requestMatchers(HttpMethod.POST, "/Guide/**/uploadImage").permitAll()
                        .requestMatchers("/Guide/**/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Guide/**/image").permitAll()
                        .requestMatchers("/Guide/updateGuide/**").permitAll()
                        .requestMatchers("/Planning/addPlanning").permitAll()
                        .requestMatchers("/ReservationGuide/byuser/**").permitAll()
                        .requestMatchers("/ReservationGuide/byuser/**").permitAll()
                        .requestMatchers("/api/stats/users/count").permitAll()
                        .requestMatchers("/api/stats/activities/count").permitAll()
                        .requestMatchers("/api/stats/blogs/count").permitAll()
                        .requestMatchers("/api/stats/restaurants/count").permitAll()
                        .requestMatchers("/api/stores/users/count").permitAll()
                        .requestMatchers("/api/guides/users/count").permitAll()
                        .requestMatchers("/Planning/addReservedPlanning").permitAll()
                        .requestMatchers("/Planning/addguidetoProject/**").permitAll()
                        .requestMatchers("/Planning/updateGuide").permitAll()
                        .requestMatchers("/Planning/viewPlanning").permitAll()
                        .requestMatchers("/Planning/isReserved").permitAll()
                        .requestMatchers("/Planning/getAllByguide/**").permitAll()
                        .requestMatchers("/Planning/isReserved").permitAll()
                        .requestMatchers("/Planning/isReserved").permitAll()
                        .requestMatchers("/email/sendemail").permitAll()

                        .requestMatchers("/Planning/getOne/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/updateReservationGuide/**").permitAll() // Adjust access




                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/user-id/**").permitAll()
                        .requestMatchers("/chat/**").permitAll()



                        .requestMatchers("/Guide/**").permitAll()
                        .requestMatchers("/Guide/**").permitAll()
                        .requestMatchers("/ReservationGuide/**").permitAll()
                        .requestMatchers("/ReservationGuide/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/Guide/updateGuide/**").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.DELETE, "/Guide/deleteGuide/").permitAll() // Adjust access
                        .requestMatchers(HttpMethod.POST, "/Guide/**/uploadImage").permitAll()
                        .requestMatchers("/Guide/**/image").permitAll()
                        .requestMatchers(HttpMethod.GET, "/Guide/**/image").permitAll()
                        .requestMatchers("/Guide/updateGuide/**").permitAll()
                        .requestMatchers("/Guide//uploads/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/**/status").permitAll() // Adjust access

                        .requestMatchers(HttpMethod.PUT, "/ReservationGuide/updateReservationGuide/**").permitAll() // Adjust access
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/auth/signup").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/profile/complete/").permitAll()
                        .requestMatchers("/Guide/deleteGuide/").permitAll()
                        .requestMatchers("/api/**").permitAll() // Allow access to all API endpoints
                        .requestMatchers("/Guide/**").permitAll()
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
