package com.amzmall.project.users.domain.controller;

import com.amzmall.project.users.domain.entity.Users;
import com.amzmall.project.users.repository.UsersRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class usersController {

    @Autowired
    private UsersRepository userRepository;

    @GetMapping(value = "")
    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Users> getUser(@PathVariable("id") int id) {
        return userRepository.findById(id);
    }
}
