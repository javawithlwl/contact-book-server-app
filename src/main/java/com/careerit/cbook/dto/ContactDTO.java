package com.careerit.cbook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
  @JsonFormat(pattern="yyyy-MM-dd")
  private LocalDate dob;
  private String phone;

}
