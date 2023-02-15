package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.ContactDTO;
import com.careerit.cbook.exception.ContactBookException;
import com.careerit.cbook.repo.ContactRepo;
import com.careerit.cbook.util.ConvertorUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactRepo contactRepo;


  @Override
  public Page<ContactDTO> getAllContacts(Pageable pageable) {
    Page<Contact> contactPage = contactRepo.findAll(pageable);
    List<ContactDTO> contactList = contactPage.stream()
        .map(ele -> ConvertorUtil.convert(ele, ContactDTO.class))
        .collect(Collectors.toList());
    return new PageImpl<>(contactList,pageable,contactPage.getTotalElements());
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
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void delete(String id) {
        Assert.notNull(id,"To contact id can't be null or empty");
        Contact contact = contactRepo.findById(id)
            .orElseThrow(() -> {
                log.error("Contact is not found, with id {}",id);
                return new IllegalArgumentException("Contact is not found for given id" + id);
                });
        contactRepo.delete(contact);
        log.info("Contact deleted with id :{}",id);
  }

  @Override
  public File export() {
    List<Contact> list = contactRepo.findAll();
    String[] headings = new String[]{"Id","Name","Email","Dob","Mobile"};
    List<String[]> data = new ArrayList<>();
    data.add(headings);
    for(Contact contact:list){
        int i=0;
        String[] arr = new String[5];
        arr[i++] = contact.getId();
        arr[i++]  = contact.getName();
        arr[i++] = contact.getEmail();
        arr[i++]  = contact.getDob().toString();
        arr[i] = contact.getPhone();
        data.add(arr);
    }
    File file = getTempfile();
    writeAllLines(data,file.toPath());
    return file;
  }

  public void writeAllLines(List<String[]> lines, Path path)  {
    try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
      writer.writeAll(lines);
    }catch (IOException e){
        log.error("{}",e);
    }
  }
  private File getTempfile(){
    return new File(System.getProperty("java.io.tmpdir") + "/" +System.currentTimeMillis()+"_"+"contacts.csv");
  }
  @Override
  public List<ContactDTO> addAll(List<ContactDTO> list) {
      return Collections.emptyList();
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
        contact.setName(ele[0]); contact.setEmail(ele[1]); contact.setPhone(ele[2]);
        contact.setDob(LocalDate.parse(ele[3]));
        return contact;
      }).collect(Collectors.toList());
      log.info("Contact counts in file {} is {}",file.getName(),contacts.size());
      List<Contact> contactList = contactRepo.saveAll(contacts);
      contactListDto = contactList.stream()
          .map(ele -> ConvertorUtil.convert(ele, ContactDTO.class))
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new ContactBookException("Reading file :"+e.getMessage());
    }
    return contactListDto;
  }

  private File covertMultipartToFile(MultipartFile multipart) {
    File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getName());
    try {
      multipart.transferTo(convFile);
    } catch (IOException e) {
      throw new ContactBookException("File not found,Check file permission or file location");
    }
    return convFile;
  }

  private List<String[]> readAllLines(Path filePath) throws IOException {
    try (Reader reader = Files.newBufferedReader(filePath)) {
      try (CSVReader csvReader = new CSVReader(reader)) {
        return csvReader.readAll();
      }
    }
  }
}
