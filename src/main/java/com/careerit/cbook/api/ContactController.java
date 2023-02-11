package com.careerit.cbook.api;

import com.careerit.cbook.dto.ContactDTO;
import com.careerit.cbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

  @Autowired
  private ContactService contactService;

  @PostMapping("/addnew")
  public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
    ContactDTO newContact = contactService.addContact(contactDTO);
    return ResponseEntity.ok(newContact);
  }

  @GetMapping("/list")
  public ResponseEntity<List<ContactDTO>> getContacts() {
    List<ContactDTO> contacts = contactService.getAllContacts();
    return ResponseEntity.ok(contacts);
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadContacts(@RequestParam("file") MultipartFile file) {
    contactService.upload(file);
    String message = "Contact added successfully";
    return ResponseEntity.ok(message);
  }


  @GetMapping("/greet")
  public ResponseEntity<String> greet() {
    return ResponseEntity.ok("Welcome to Contact Book Spring Boot Application");
  }
}
