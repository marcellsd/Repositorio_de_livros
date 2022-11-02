package com.ufrn.imd.web2.projeto01.livros.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ufrn.imd.web2.projeto01.livros.models.User;
import com.ufrn.imd.web2.projeto01.livros.services.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        String cryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(cryptPassword);
        return userService.saveUser(user);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userService.getUserById(id);

        updatedUser.setId(user.getId());
        if(updatedUser.getIsAuthor() == null)  updatedUser.setIsAuthor(user.getIsAuthor());
        if(updatedUser.getIsPublisher() == null) updatedUser.setIsPublisher(user.getIsPublisher());
        if(updatedUser.getPassword() == null) updatedUser.setPassword(user.getPassword());
        if(updatedUser.getUsername() != null) {throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot change username");}
        else{
           updatedUser.setUsername(user.getUsername()); 
        };
        return userService.saveUser(updatedUser);
    }



}
