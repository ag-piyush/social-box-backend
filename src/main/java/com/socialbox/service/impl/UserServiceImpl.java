package com.socialbox.service.impl;

import com.socialbox.model.User;
import com.socialbox.repository.UserRepository;
import com.socialbox.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    public User getUserById(String id){
        return this.userRepository.findById(id).get();
    }

    public User saveUser(User user){
        return this.userRepository.save(user);
    }
}
