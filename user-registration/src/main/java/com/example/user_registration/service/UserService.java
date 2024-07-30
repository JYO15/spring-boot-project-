package com.example.user_registration.service;

import com.example.user_registration.model.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
}