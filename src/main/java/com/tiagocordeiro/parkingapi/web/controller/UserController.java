package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.UserCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.UserResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateDto objDto) {
        User user = service.create(UserMapper.toUser(objDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User obj) {
        User user = service.updatePassword(id, obj.getPassword());
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> list = service.getAll();
        return ResponseEntity.ok().body(list);
    }

}
