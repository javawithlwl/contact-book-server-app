package com.careerit.cbook.repo;

import com.careerit.cbook.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ContactRepo extends MongoRepository<Contact,String> {

      List<Contact> findByNameContaining(String str);
}
