package com.careerit.cbook.service;

import com.careerit.cbook.dto.ContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;


public interface ContactService {
  Page<ContactDTO> getAllContacts(Pageable pageable);
  ContactDTO getById(String id);
  ContactDTO addContact(ContactDTO contactDTO);
  List<ContactDTO> search(String str);
  void delete(String id);
  File export();
  List<ContactDTO> addAll(List<ContactDTO> list);
  List<ContactDTO> upload(MultipartFile file);
}