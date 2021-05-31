package com.socialbox.controllers;

import com.socialbox.model.User;
import com.socialbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id){
        return this.userService.getUserById(id);
    }

    @PostMapping
    public User saveUser(@RequestBody User user){
        return this.userService.saveUser(user);
    }
}
