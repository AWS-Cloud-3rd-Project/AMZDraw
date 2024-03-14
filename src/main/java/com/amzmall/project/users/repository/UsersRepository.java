package com.amzmall.project.users.repository;

import com.amzmall.project.users.domain.dto.UsersResDto;
import com.amzmall.project.users.domain.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.isActivate = true")
    List<Users> findAllActivateUsers();

    @Query("SELECT u FROM Users u WHERE u.isActivate = false")
    List<Users> findAllDeActivateUsers();
}
