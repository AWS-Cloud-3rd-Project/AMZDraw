package com.amzmall.project.admin.repository;

import com.amzmall.project.admin.domain.user.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository<AdminUser> extends JpaRepository<AdminUser, Long> {
    Optional<AdminUser> findByEmailAndIsActivated(String userEmail, Boolean isActivated);
}
