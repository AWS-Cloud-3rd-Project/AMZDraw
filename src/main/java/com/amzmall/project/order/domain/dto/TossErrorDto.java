package com.amzmall.project.order.domain.dto;

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
