package com.amzmall.project.users.service;

import com.amzmall.project.users.domain.entity.Users;
import com.amzmall.project.users.domain.dto.UsersResDto;
import com.amzmall.project.users.repository.UsersRepository;
import com.amzmall.project.util.exception.BusinessException;
import com.amzmall.project.util.advice.ExMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public UsersResDto findById(int id) {
        Users users = usersRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND));
        return users.toDto();
    }
    
    @Transactional(readOnly = true)
    public UsersResDto findByEmail(String email) {
        Users users = usersRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND));
        return users.toDto();
    }

    @Transactional(readOnly = true)
    public List<UsersResDto> findAll() {
        return usersRepository.findAll()
            .stream()
            .map(Users::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsersResDto> findAllActiveUsers() {
        return usersRepository.findAllActivateUsers()
            .stream()
            .map(Users::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsersResDto> findAllDeActiveUsers() {
        return usersRepository.findAllDeActivateUsers()
            .stream()
            .map(Users::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public void deActivateUser(String email) {
        Users users = usersRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND));
        users.setActivate(false);
    }

    @Transactional(readOnly = true)
    public int findUserId(String email) {
        return usersRepository.findByEmail(email)
            .orElseThrow(() -> new BusinessException(ExMessage.USER_ERROR_NOT_FOUND))
            .getId();
    }

}
