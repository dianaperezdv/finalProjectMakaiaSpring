package com.example.finalProject.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {

    @Value("${username}")
    private String usernameAdmin;

    @Value("${password}")
    private String passwordAdmin;

    @Value("${usernameUser}")
    private String usernameUser;

    @Value("${passwordUser}")
    private String passwordUser;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET).hasAuthority("read")
                .antMatchers(HttpMethod.POST).hasAuthority("write")
                .antMatchers(HttpMethod.PUT).hasAuthority("write")
                .antMatchers(HttpMethod.DELETE).hasAuthority("write")
                .and()
                .csrf().disable()
                .build();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.withUsername(usernameUser)
                        .password(passwordEncoder().encode(passwordUser))
                        .authorities("read")
                        .build(),
                User.withUsername(usernameAdmin)
                        .password(passwordEncoder().encode(passwordAdmin))
                        .authorities("read","write")
                        .build()
        );
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
}