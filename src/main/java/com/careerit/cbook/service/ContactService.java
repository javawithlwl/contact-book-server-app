package com.careerit.cbook.service;

import com.careerit.cbook.dto.ContactDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.multipart.MultipartFile;

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
  public static String getName(){
    return null;
  }
}