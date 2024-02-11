package com.amzmall.project.domain.dto;

import lombok.*;

import java.sql.Timestamp;

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
