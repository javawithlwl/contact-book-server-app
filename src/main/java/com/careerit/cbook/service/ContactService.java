package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.ContactDTO;
import com.careerit.cbook.repo.ContactRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


public interface ContactService {
  List<ContactDTO> getAllContacts();
  default Page<ContactDTO> getContact(Pageable pageable){
    return null;
  }
  ContactDTO getById(String id);
  ContactDTO addContact(ContactDTO contactDTO);
  List<ContactDTO> search(String str);
  void delete(String id);
  void export();
  List<ContactDTO> addAll(List<ContactDTO> list);
  List<ContactDTO> upload(MultipartFile file);
  private List<ContactDTO> fullSearch(String str){
    return null;
  }
}