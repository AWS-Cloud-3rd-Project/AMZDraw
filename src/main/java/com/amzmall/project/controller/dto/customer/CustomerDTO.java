package com.amzmall.project.controller.dto.customer;

import lombok.Data;
import com.amzmall.project.domain.customer.Customer;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;

@Data(staticConstructor = "of")
public class CustomerDTO {
    private final Long customerId;
    private final String customerName;
    private final String email;
    private final String address;
    private final String phoneNumber;
    private final Date birth;
    private final String zipCode;


    public static CustomerDTO of(Customer customer) {
        return CustomerDTO.of(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getBirth(),
                customer.getzipCode());
    }

}
