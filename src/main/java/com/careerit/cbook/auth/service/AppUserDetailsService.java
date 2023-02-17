package com.careerit.cbook.auth.service;

import com.careerit.cbook.auth.repo.AppUserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

  private final AppUserRepo appUserRepo;
  //private final PasswordEncoder passwordEncoder;
  //private List<UserDetails> userDetails;

//  @PostConstruct
//  public void init() {
//    userDetails = new ArrayList<>(List.of(
//        User.withUsername("user")
//            .password(passwordEncoder.encode("user@123")).authorities("ROLE_USER").build(),
//        User.withUsername("admin")
//            .password(passwordEncoder.encode("admin@123")).authorities("ROLE_ADMIN").build()
//    ));
//  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    return userDetails.stream()
//        .filter(user -> user.getUsername().equals(username))
//        .findFirst()
//        .orElseThrow(() -> new UsernameNotFoundException("User not found with given username"));

    return appUserRepo.findByUsername(username)
        .map(ele-> {
          UserDetails user = User.withUsername(ele.getUsername())
                                 .password(ele.getPassword())
                                 .authorities("ROLE_USER")
                                 .build();
          return user;
        })
        .orElseThrow(() -> new UsernameNotFoundException("User not found with given username"));
  }
}