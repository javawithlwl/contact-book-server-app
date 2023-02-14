package com.careerit.cbook.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppResponse {
    private String message;
}
