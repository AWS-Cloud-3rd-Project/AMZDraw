package com.amzmall.project.admin.enums;

import com.nimbusds.oauth2.sdk.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
@RequiredArgsConstructor
public enum AdminUserRole {
    SUPER_AD("ADMIN_SUPER", "총 관리자"),

    PRODUCT_AD("ADMIN_PRODUCT","상품 관리자"),
    MEMBER_AD("ADMIN_MEMBER","회원 관리자"),
    ORDER_AD("ADMIN_ORDER","주문 관리자");

    private final String key;
    private final String title;
}