package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User updatePassword(Long id, String password) {
        User user = findById(id);
        user.setPassword(password);
        return user;
    }
}
