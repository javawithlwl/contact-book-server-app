package com.careerit.cbook.api;

import com.careerit.cbook.dto.AppResponse;
import com.careerit.cbook.dto.ContactDTO;
import com.careerit.cbook.service.ContactService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
  public ResponseEntity<Page<ContactDTO>> getContacts(@ParameterObject Pageable pageable) {
    Page<ContactDTO> contacts = contactService.getAllContacts(pageable);
    return ResponseEntity.ok(contacts);
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadContacts(@RequestParam("file") MultipartFile file) {
    contactService.upload(file);
    String message = "Contact added successfully";
    return ResponseEntity.ok(message);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<AppResponse> deleteContact(@PathVariable("id") String id) {
    contactService.delete(id);
    AppResponse appResponse = AppResponse.builder()
        .message("Contact with id " + id + " deleted successfully")
        .build();
    return ResponseEntity.ok(appResponse);
  }

  //Export
  @GetMapping("/export")
  public void downloadContacts(HttpServletResponse response) throws IOException {

    File file = contactService.export();
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
    ByteArrayInputStream bis = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
    IOUtils.copy(bis, response.getOutputStream());
  }

  // Search

  // Find by id

  // Update

  @GetMapping("/greet")
  public ResponseEntity<String> greet() {
    return ResponseEntity.ok("Welcome to Contact Book Spring Boot Application");
  }
}
