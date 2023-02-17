package com.careerit.cbook.auth.domain;

import lombok.Data;

@Data
public class LoginRequest {
  private String username;
  private String password;
}