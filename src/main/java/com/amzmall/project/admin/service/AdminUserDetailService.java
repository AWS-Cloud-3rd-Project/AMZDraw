package com.amzmall.project.admin.service;

import com.amzmall.project.admin.domain.user.AdminUser;
import com.amzmall.project.admin.domain.user.AdminUserDetail;
import com.amzmall.project.admin.exception.NotFoundAdminUserException;
import com.amzmall.project.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserDetailService implements UserDetailsService {

    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        log.info(">>> loadUserByUsername, {}", userEmail);
        //DaoAuthenticationProvider 방식: 데이터베이스에서 정보를 가져와 UserDetailsService에게 전달
        Optional<AdminUser> optionalAdminUser = this.adminUserRepository.findByEmailAndIsActivated(userEmail, true);
        if (optionalAdminUser.isEmpty()) {
            throw new NotFoundAdminUserException("Not found admin user with " + userEmail);
        }

        AdminUser adminUser = optionalAdminUser.get();
        AdminUserDetail adminUserDetail = new AdminUserDetail();
        adminUserDetail.setAdminUser(adminUser);
        adminUserDetail.setRoles(Arrays.asList(adminUser.getRole()));
        adminUserDetail.setPermissions(Arrays.asList(adminUser.getPermission()));

        return adminUserDetail;
    }
}