package com.amzmall.project.payment.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    A("사용 가능"),
    D("사용 불가능");
    private final String name;
}
