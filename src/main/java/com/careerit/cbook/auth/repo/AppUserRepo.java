package com.careerit.cbook.auth.repo;

import com.careerit.cbook.auth.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepo extends MongoRepository<AppUser,String> {

      Optional<AppUser> findByUsername(String username);
}
