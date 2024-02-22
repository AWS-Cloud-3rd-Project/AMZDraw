package com.amzmall.project.domain.customer;

import ch.qos.logback.classic.encoder.JsonEncoder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import com.amzmall.project.enums.ECommerceRole;
import lombok.Getter;
import java.sql.Date;
import java.time.OffsetDateTime;

@Schema(description = "회원 DTO")
@Getter
public class MemberDto {
    //  @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "아이디")
    private Long customerId;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "전화번호")
    private String phoneNumber;
    @Schema(description = "이메일")
    private String email;

    @Schema(description = "생일")
    private Date birth;

    @Schema(description = "암호")
    private String password;

    @Schema(description = "주소")
    private String address;
    @Schema(description = "우편번호")
    private String zipCode;

    @Schema(description = "권한")
    @Enumerated(value = EnumType.STRING)
    private ECommerceRole role;

    ///@Column(name = "permission")
    ///@Enumerated(value = EnumType.STRING)
    ///private CustomerPermission permission;

    //@Column(name = "is_deleted")
    //private boolean isDeleted = false;

    @Schema(description = "생성 날짜")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Schema(description = "수정일자")
    @Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // @Column(name = "status", nullable = false, columnDefinition = "ENUM('A','D') DEFAULT 'A'")
    //@Enumerated(EnumType.STRING)
    //private Status status;

    public MemberDto(Customer customer) {

        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.name = customer.getName();
        this.address = customer.getAddress();
      //  this.zipCode = customer.getZipCode();
        this.birth = (Date) customer.getBirth();
        this.role = ECommerceRole.CUSTOMER;
        this.password = customer.getPassword();

    }
}


