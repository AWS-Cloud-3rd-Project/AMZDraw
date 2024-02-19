package com.amzmall.project.controller.dto.customer;

import lombok.Data;

import java.util.Date;

@Data(staticConstructor = "of")
public class CustomerRegisterDTO {
    private String username;
    private String phoneNumber;
    private Date birth;
    private String zipCode;
    private String address;
    private String email;
    private String password1;
    private String password2;
}
