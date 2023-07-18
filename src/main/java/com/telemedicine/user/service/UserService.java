package com.telemedicine.user.service;

import com.telemedicine.user.model.dto.User;

import java.util.UUID;

public interface UserService {
    User registerUser(User user);
    User findUserById(UUID id);
    User getUserByEmail(String email);
    User updateUser(UUID id,User updatedUser);
    User findUserByEmail(String email);
    void updateUserVerification(UUID id);
}
