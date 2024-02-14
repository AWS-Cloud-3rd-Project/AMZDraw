package com.amzmall.project.admin.service.dto;

import com.amzmall.project.admin.enums.CustomerStatus;
import lombok.Data;

import java.time.OffsetDateTime;

@Data(staticConstructor = "of")
public class CustomerDTO {
    private final Long customerId;
    private final String customerName;
    private final String phoneNumber;
    private final String address;
    private final CustomerStatus customerStatus;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
}
