package com.amzmall.project.controller;

import com.amzmall.project.entity.User;
import com.amzmall.project.repository.UserRepository;
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

    @GetMapping(value = "")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<User> getUser(@PathVariable("id") Long id){
        return userRepository.findById(id);
    }
}
