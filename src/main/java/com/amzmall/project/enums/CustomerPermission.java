package com.amzmall.project.enums;

public enum CustomerPermission {
    GENERAL("일반");

    private String permissionDesc;

    CustomerPermission(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }
}
