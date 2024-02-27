package com.amzmall.project.admin.domain.user;

import com.amzmall.project.admin.enums.AdminUserPermission;
import com.amzmall.project.admin.enums.AdminUserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminUserDetail implements UserDetails {
    private AdminUser adminUser;
    private List<AdminUserRole> roles; //역할
    private List<AdminUserPermission> permissions; //접근(권한)

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //해당 유저의 권한 목록
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (AdminUserPermission permission : permissions) {
            GrantedAuthority permissionAuthority = new SimpleGrantedAuthority(permission.name());
            authorities.add(permissionAuthority);
        }
        for (AdminUserRole role : roles) {
            GrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + role.name());
            authorities.add(roleAuthority);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.adminUser.getPassword();
    }

    public String getUsername() {
        return this.adminUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.adminUser.isActivated();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.adminUser.isActivated();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.adminUser.isActivated();
    }

    @Override
    public boolean isEnabled() {
        return this.adminUser.isActivated();
    }

    public Iterable<String> getPermissionList() {
        return permissions.stream().map(p -> p.name()).collect(Collectors.toList());
    }

    public Iterable<String> getRoleList() {
        return roles.stream().map(r -> r.name()).collect(Collectors.toList());
    }
}