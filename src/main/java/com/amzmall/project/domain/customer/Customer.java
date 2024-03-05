package com.amzmall.project.domain.customer;

import jakarta.persistence.*;
import lombok.Data;
import com.amzmall.project.controller.dto.customer.CustomerRegisterDTO;
import com.amzmall.project.enums.CustomerPermission;
import com.amzmall.project.enums.ECommerceRole;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.OffsetDateTime;

@Entity
@Table(name = "amzmall", schema = "nydb")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long customerId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false)
    private String phoneNumber;
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "birth")
    private java.sql.Date birth;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;
    @Column(name = "zip_code", length = 5)
    private String zipCode;

    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private ECommerceRole role;

    ///@Column(name = "permission")
    ///@Enumerated(value = EnumType.STRING)
    ///private CustomerPermission permission;

    //@Column(name = "is_deleted")
    //private boolean isDeleted = false;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // @Column(name = "status", nullable = false, columnDefinition = "ENUM('A','D') DEFAULT 'A'")
    //@Enumerated(EnumType.STRING)
    //private Status status;

    public static Customer createCustomer(CustomerRegisterDTO customerRegisterDTO, PasswordEncoder passwordEncoder) {

        Customer customer = new Customer();
        customer.phoneNumber = customerRegisterDTO.getPhoneNumber();
        customer.email = customerRegisterDTO.getEmail();
        customer.name = customerRegisterDTO.getUsername();
        customer.address = customerRegisterDTO.getAddress();
        customer.zipCode = customerRegisterDTO.getZipCode();
        customer.birth = (Date) customerRegisterDTO.getBirth();
        customer.role = ECommerceRole.CUSTOMER;
        customer.password = passwordEncoder.encode(customerRegisterDTO.getPassword1());

        return customer;
    }
}
