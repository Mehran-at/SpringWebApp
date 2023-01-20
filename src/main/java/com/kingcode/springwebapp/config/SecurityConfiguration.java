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
            .antMatchers(HttpMethod.POST, "/admin/**")
            .access("hasRole('ADMIN')")

            .antMatchers("/design", "/orders").access("hasRole('USER')")
            .antMatchers("/", "/**").access("permitAll")

            .and()
            .formLogin()
            .loginPage("/login")

            .and()
            .oauth2Login()
            .loginPage("/login")


            .and()
            .logout()
            .logoutSuccessUrl("/login")

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

//
// IN MEMORY AUTHENTICATION EXAMPLE
//
/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth
      .inMemoryAuthentication()
        .withUser("buzz")
          .password("infinity")
          .authorities("ROLE_USER")
        .and()
        .withUser("woody")
          .password("bullseye")
          .authorities("ROLE_USER");
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userDetailsService);
  }
*/

//
// JDBC Authentication example
//
/*
  @Autowired
  DataSource dataSource;
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
        .dataSource(dataSource);
  }
*/


/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, enabled from Users " +
            "where username=?")
        .authoritiesByUsernameQuery(
            "select username, authority from UserAuthorities " +
            "where username=?");
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, enabled from Users " +
            "where username=?")
        .authoritiesByUsernameQuery(
            "select username, authority from UserAuthorities " +
            "where username=?")
        .passwordEncoder(new BCryptPasswordEncoder());
  }
*/


//
// LDAP Authentication example
//
/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchFilter("(uid={0})")
        .groupSearchFilter("member={0}");
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}");
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .passwordCompare();
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .passwordCompare()
        .passwordEncoder(new BCryptPasswordEncoder())
        .passwordAttribute("passcode");
  }
*/

/*
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  auth
    .ldapAuthentication()
      .userSearchBase("ou=people")
      .userSearchFilter("(uid={0})")
      .groupSearchBase("ou=groups")
      .groupSearchFilter("member={0}")
      .passwordCompare()
      .passwordEncoder(new BCryptPasswordEncoder())
      .passwordAttribute("passcode")
      .and()
      .contextSource()
        .url("ldap://tacocloud.com:389/dc=tacocloud,dc=com");
}
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .passwordCompare()
        .passwordEncoder(new BCryptPasswordEncoder())
        .passwordAttribute("passcode")
        .and()
        .contextSource()
          .root("dc=tacocloud,dc=com");
  }
*/

/*
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .passwordCompare()
        .passwordEncoder(new BCryptPasswordEncoder())
        .passwordAttribute("passcode")
        .and()
        .contextSource()
          .root("dc=tacocloud,dc=com")
          .ldif("classpath:users.ldif");
  }
*/

}
