package com.amzmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResCancelDto {
    Long cancelAmount;
    String cancelReason;
    String canceledAt;
}
