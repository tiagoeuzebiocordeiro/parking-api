package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public User create(User obj) {
        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("New password doesn't match with confirm password.");
        }
        User user = findById(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("The current password of this user doesn't match!");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }
}
