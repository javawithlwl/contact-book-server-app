package com.careerit.cbook.auth.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class AppUser {
    @Id
    private String id;
    private String username;
    private String password;
}
