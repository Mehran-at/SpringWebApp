package com.kingcode.springwebapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/admin/**")
//            .access("hasRole('ADMIN')")

            .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
            .antMatchers("/api/**")
            .permitAll()

            //.access("hasRole('USER')")
            .antMatchers(HttpMethod.PATCH, "/api/ingredients").permitAll()
            .antMatchers("/**")
            .access("permitAll")

            .and()
            .formLogin()
            .loginPage("/login")

            .and()
            .oauth2Login()
            .loginPage("/login")

            .and()
            .httpBasic()
            .realmName("Taco Cloud")

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
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

    }
}
