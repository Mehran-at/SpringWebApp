package com.kingcode.springwebapp.user;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    private Long id;
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;


    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        List<UserDetails> usersList = new ArrayList<>();
        usersList.add(new User(
            "buzz", encoder.encode("password"),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        usersList.add(new User(
            "woody", encoder.encode("password"),
            List.of(new SimpleGrantedAuthority("ROLE_USER"))));
        return new InMemoryUserDetailsManager(usersList);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

