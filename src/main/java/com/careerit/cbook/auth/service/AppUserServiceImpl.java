package com.careerit.cbook.auth.service;

import com.careerit.cbook.auth.domain.AppUser;
import com.careerit.cbook.auth.repo.AppUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements  AppUserService{

  private final AppUserRepo appUserRepo;
  private final PasswordEncoder passwordEncoder;
  @Override
  public AppUser register(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser = appUserRepo.save(appUser);
        return appUser;
  }
}
