package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.ContactDTO;
import com.careerit.cbook.repo.ContactRepo;
import com.careerit.cbook.util.ConvertorUtil;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactRepo contactRepo;


  @Override
  public List<ContactDTO> getAllContacts() {
    List<Contact> contacts = contactRepo.findAll();
    List<ContactDTO> contactList = contacts.stream()
        .map(ele -> ConvertorUtil.convert(ele, ContactDTO.class))
        .collect(Collectors.toList());
    return contactList;
  }

  @Override
  public ContactDTO getById(String id) {
    return null;
  }

  @Override
  public ContactDTO addContact(ContactDTO contactDTO) {
    Assert.notNull(contactDTO.getName(), "Name can't be null or empty");
    Contact contact = ConvertorUtil.convert(contactDTO, Contact.class);
    contact = contactRepo.save(contact);
    contactDTO = ConvertorUtil.convert(contact, ContactDTO.class);
    log.info("Contact is added with id {} and name {}", contactDTO.getId(), contactDTO.getName());
    return contactDTO;
  }

  @Override
  public List<ContactDTO> search(String str) {
    return null;
  }

  @Override
  public void delete(String id) {

  }

  @Override
  public void export() {

  }

  @Override
  public List<ContactDTO> addAll(List<ContactDTO> list) {
    return null;
  }

  @Override
  public List<ContactDTO> upload(MultipartFile file) {
    List<ContactDTO> contactListDto = new ArrayList<>();
    File conFile = covertMultipartToFile(file);
    try {
      List<String[]> list = readAllLines(conFile.toPath());
      log.info("File has {} rows",list.size());
      List<Contact> contacts = list.stream().skip(1).map(ele -> {
        Contact contact = new Contact();
        contact.setName(ele[0]);
        contact.setEmail(ele[1]);
        contact.setPhone(ele[2]);
        contact.setDob(LocalDate.parse(ele[3]));
        return contact;
      }).collect(Collectors.toList());
      log.info("Contact counts in file {} is {}",file.getName(),contacts.size());
      List<Contact> contactList = contactRepo.saveAll(contacts);
      contactListDto = contactList.stream()
          .map(ele -> ConvertorUtil.convert(ele, ContactDTO.class))
          .collect(Collectors.toList());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return contactListDto;
  }

  private File covertMultipartToFile(MultipartFile multipart) {
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getName());
    try {
      multipart.transferTo(convFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return convFile;
  }

  private List<String[]> readAllLines(Path filePath) throws Exception {
    try (Reader reader = Files.newBufferedReader(filePath)) {
      try (CSVReader csvReader = new CSVReader(reader)) {
        return csvReader.readAll();
      }
    }
  }
}
