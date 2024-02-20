package com.amzmall.project.payment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TossErrorDto {
    String code;
    String message;
}
