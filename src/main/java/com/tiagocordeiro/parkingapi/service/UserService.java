package com.tiagocordeiro.parkingapi.service;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.exception.EntityNotFoundException;
import com.tiagocordeiro.parkingapi.exception.InvalidPasswordException;
import com.tiagocordeiro.parkingapi.exception.UsernameUniqueViolationException;
import com.tiagocordeiro.parkingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return repository.save(obj);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username [%s] already registered.", obj.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User of id = %s not found.",id)));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidPasswordException("New password doesn't match with confirm password.");
        }
        User user = findById(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new InvalidPasswordException("The current password of this user doesn't match!");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(String.format("User with '%s' not found.", username)));
    }

    @Transactional(readOnly = true)
    public User.Role findRoleByUsername(String username) {
        return repository.findRoleByUsername(username);
    }
}
