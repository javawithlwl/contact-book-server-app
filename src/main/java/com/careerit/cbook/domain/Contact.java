package com.careerit.cbook.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Getter
@Setter
public class Contact {

  @Id
  private String id;
  private String name;
  private String email;
  private LocalDate dob;
  private String phone;
}
