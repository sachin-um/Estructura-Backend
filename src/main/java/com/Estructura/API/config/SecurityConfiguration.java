package com.Estructura.API.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/api/v1/retailitems/all")
                        .permitAll()
                        .requestMatchers("/api/v1/projects/**")
                        .permitAll()
                        .requestMatchers("/api/v1/blogs/**")
                        .permitAll()
                        .requestMatchers("/files/**")
                        .permitAll()
                        .requestMatchers("/api/v1/retailitems/**")
                        .permitAll()
                        .requestMatchers("/api/v1/serviceProviders/**")
                        .permitAll()
                        .requestMatchers(("/api/v1/professionals/**"))
                        .permitAll()
                        .requestMatchers(("/api/v1/rentingStore/**"))
                        .permitAll()
                        .requestMatchers(("/api/v1/retailStore/**"))
                        .permitAll()
                        .requestMatchers(("/api/v1/users/**"))
                        .permitAll()
                        .requestMatchers(("/api/v1/renting-items/**"))
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(sess->sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)
                .logout(l->l
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                SecurityContextHolder.clearContext())
                )
                // .formLogin(Customizer.withDefaults())
                // .exceptionHandling(e->e.accessDeniedPage("/access-denied"))
                // .oauth2Login(l->l.defaultSuccessUrl("/api/v1/retailitems/all"))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
