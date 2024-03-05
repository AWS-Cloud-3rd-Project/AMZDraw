package com.amzmall.project.controller.dto.customer;

import lombok.Data;

@Data
public class CustomerUpdateDTO {
    private String username;
    private String phoneNumber;
    private String zipCode;
    private String address;
}
