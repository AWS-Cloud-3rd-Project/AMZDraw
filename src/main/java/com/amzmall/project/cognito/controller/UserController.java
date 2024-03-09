package com.amzmall.project.cognito.controller;

import com.amzmall.project.cognito.entity.User;
import com.amzmall.project.cognito.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // 프론트에서 인가코드 돌려 받는 주소
    // 인가 코드로 엑세스 토큰 발급 -> 사용자 정보 조회 -> DB 저장 -> jwt 토큰 발급 -> 프론트에 토큰 전달
    @GetMapping(value = "")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<User> getUser(@PathVariable("id") Long id){
        return userRepository.findById(id);
    }
}
