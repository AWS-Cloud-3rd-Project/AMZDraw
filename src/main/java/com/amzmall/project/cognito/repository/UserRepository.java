package com.amzmall.project.cognito.repository;

import com.amzmall.project.cognito.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}