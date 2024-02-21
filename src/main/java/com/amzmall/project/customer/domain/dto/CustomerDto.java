package com.amzmall.project.customer.domain.dto;

import java.sql.Timestamp;
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
public class CustomerDto {
    private Long customerId;
    private String email;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
