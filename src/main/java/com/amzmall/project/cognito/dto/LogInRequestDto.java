package com.amzmall.project.cognito.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInRequestDto {
    private String username;
    private String password;
}
