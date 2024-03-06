package com.amzmall.project.repository;

import com.amzmall.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}