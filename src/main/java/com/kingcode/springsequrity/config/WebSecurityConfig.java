package com.kingcode.springsequrity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    // We will need a page that the authorization server provides to the user, when its redirected and now user have to
    // put their username and password.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin()
                .and()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var u1 = User.withUsername("bill").password("12345").authorities("read").build();
        var uds = new InMemoryUserDetailsManager();
        uds.createUser(u1);
        return uds;
    }

    // You don't want to use password encoder for your production code, but something like ByCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}