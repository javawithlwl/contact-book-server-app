package com.careerit.cbook.repo;

import com.careerit.cbook.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContactRepo extends MongoRepository<Contact,String> {
}
