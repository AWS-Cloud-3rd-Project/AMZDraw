package com.amzmall.project.users.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResDto {
    private int id;
    private String email;
    private String name;
    private boolean isActivate;
    private Role role;
    private String createdAt;
}
