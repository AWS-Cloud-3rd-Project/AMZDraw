package com.amzmall.project.users.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER("고객"),
    ADMIN("관리자");
    private final String name;
}
