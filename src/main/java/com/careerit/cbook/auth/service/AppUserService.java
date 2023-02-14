package com.careerit.cbook.auth.service;

import com.careerit.cbook.auth.domain.AppUser;

public interface AppUserService {

      public AppUser registerUser(AppUser appUser);
      public AppUser findByUsername(String username);
}
