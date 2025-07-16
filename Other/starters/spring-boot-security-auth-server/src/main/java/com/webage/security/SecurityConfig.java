package com.webage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//  Establish this application as an Authorization Server.
//  Principals are expected to be authenticated with HTTP basic.
//  Authenticated calls to /token return a JWT signed with a private key.

@Configuration
@EnableWebSecurity
public class SecurityConfig {

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         return http
             //  Disable sessions.  We want a stateless application:
             .sessionManagement(
                 session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

             //  CSRF protection is merely extra overhead with session management disabled:
             .csrf(csrf -> csrf.disable())

             //  All inbound requests must be authenticated:
             .authorizeHttpRequests( auth -> auth
                 .requestMatchers("/").permitAll()
                 .anyRequest().authenticated()
             )
             .httpBasic(Customizer.withDefaults())

             .build();
     }


    //  PART 3a -
    //  Add code here to define an InMemoryUserDetailsManager bean.
    //  Have it define a user named "client" with a password "{noop}DoNotTell"
    //  ({noop} means no encryption operation on the password - ok for our lab.)
    //  Set the "read" authoritiy on this user.

    //  Override Spring Boot's default user with
    //  our own user, password, and authorities:

}
