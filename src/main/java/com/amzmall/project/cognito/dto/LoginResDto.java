package com.amzmall.project.cognito.dto;

import com.amzmall.project.users.domain.dto.Role;

public class LoginResDto {
    private int id;
    private String email;
    private String name;
    private boolean isActivate;
    private Role role;
    private String createdAt;
}
