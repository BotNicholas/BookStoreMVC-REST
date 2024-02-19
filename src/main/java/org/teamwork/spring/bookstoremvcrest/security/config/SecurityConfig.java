package org.teamwork.spring.bookstoremvcrest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //By Default DaoAuthenticationProvider is used (it uses UserDetailsService and Password Encoder)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.cors(AbstractHttpConfigurer::disable);
        security.csrf(AbstractHttpConfigurer::disable);
        security.authorizeHttpRequests(request -> request.requestMatchers("/").permitAll());
        security.authorizeHttpRequests(request -> request.requestMatchers("/register").permitAll());
        security.authorizeHttpRequests(request -> request.requestMatchers("/**").authenticated());
        //Add form based Authentication
        security.formLogin(form -> form.permitAll());
        //Add Basic Authentication
        security.httpBasic(Customizer.withDefaults());
        return security.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
