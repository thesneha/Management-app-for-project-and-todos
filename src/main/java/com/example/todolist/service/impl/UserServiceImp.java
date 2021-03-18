package com.example.todolist.service.impl;

import com.example.todolist.model.User;
import com.example.todolist.repository.UserRepository;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public User getUserById(String userId) throws Exception {
        if(StringUtils.isEmpty(userId)){
            throw new Exception("Invalid user id");
        }
        return userRepository.findByUserId(userId);
    }

    @Override
    public User createUser(String userId, String token) {
        User user = userRepository.findByUserId(userId);
        if(user == null) {
            user = new User();
        }
        user.setUserId(userId);
        user.setToken(token);
        return userRepository.save(user);
    }
}
