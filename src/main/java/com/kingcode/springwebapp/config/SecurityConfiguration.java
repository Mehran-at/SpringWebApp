package com.kingcode.springwebapp.config;

import com.kingcode.springwebapp.user.User;
import com.kingcode.springwebapp.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException(" User '" + username + "' not found!");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeRequests()
            .mvcMatchers("/design", "/orders").hasRole("USER")
            .anyRequest().permitAll()

            .and()
            .formLogin()
            .loginPage("/login")

            .and()
            .oauth2Login()
            .loginPage("/login")


            .and()
            .logout()
            .logoutSuccessUrl("/")

            // Make H2-Console non-secured; for debug purposes
            .and()
            .csrf()
            .ignoringAntMatchers("/h2-console/**")

            // Allow pages to be loaded in frames from the same origin; needed for H2-Console
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()


            .and()
            .build();
    }


    /**
     Using Spring expressions to define authorization rules with more flexibility
     */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//            .authorizeRequests()
//            .antMatchers("/design", "/orders").access("hasRole('USER')")
//            .antMatchers("/", "/**").access("permitAll()")
//
//            .and()
//            .build();
//    }

    /**
     But expressions can be much more flexible. For instance, suppose that (for some crazy reason) you wanted to allow
     only users with ROLE_USER authority to create new tacos on Tuesdays (for example, on Taco Tuesday);
     With SpEL security constraints, the possibilities are virtually endless.
     */
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//            .authorizeRequests()
//            .antMatchers("/design", "/orders")
//            .access("hasRole('USER') && " +
//                "T(java.util.Calendar).getInstance().get("+
//                "T(java.util.Calendar).DAY_OF_WEEK) == " +
//                "T(java.util.Calendar).TUESDAY")
//            .antMatchers("/", "/**").access("permitAll")
//
//            .and()
//            .build();
//    }

}
