package com.example.todolist.service;

import com.example.todolist.model.User;

public interface UserService {

    User getUserById(String userId) throws Exception;

    User createUser(String userId, String token);
}
