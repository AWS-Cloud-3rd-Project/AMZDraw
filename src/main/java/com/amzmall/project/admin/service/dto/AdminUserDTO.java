package com.amzmall.project.admin.service.dto;

import com.amzmall.project.admin.domain.user.AdminUserPermission;
import com.amzmall.project.admin.domain.user.AdminUserRole;
import lombok.Data;

@Data
public class AdminUserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private AdminUserRole role = AdminUserRole.USER;
    private AdminUserPermission permission = AdminUserPermission.ALL;
    private boolean isDeleted = false;
    private boolean isActivated = true;
}
