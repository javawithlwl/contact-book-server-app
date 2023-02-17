package com.careerit.cbook.auth.controller;

import com.careerit.cbook.auth.config.JwtUtil;
import com.careerit.cbook.auth.domain.AppUser;
import com.careerit.cbook.auth.domain.LoginRequest;
import com.careerit.cbook.auth.domain.LoginResponse;
import com.careerit.cbook.auth.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;
  private final AppUserService appUserService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
    String jwtToken = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new LoginResponse(jwtToken));
  }

  @PostMapping("/register")
  public ResponseEntity<AppUser> register(@RequestBody AppUser appUser){
        appUser=appUserService.register(appUser);
        return ResponseEntity.ok(appUser);
  }
}