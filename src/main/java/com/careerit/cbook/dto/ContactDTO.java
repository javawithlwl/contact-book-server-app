package com.careerit.cbook.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

  private String id;
  private String name;
  private String email;
  private LocalDate dob;
  private String phone;

}
