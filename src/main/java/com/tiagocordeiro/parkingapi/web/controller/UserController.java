package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.UserCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.UserPasswordDto;
import com.tiagocordeiro.parkingapi.web.dto.UserResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto objDto) {
        User user = service.create(UserMapper.toUser(objDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto objDTO) {
        User user = service.updatePassword(id, objDTO.getCurrentPassword(), objDTO.getNewPassword(), objDTO.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> list = service.getAll();
        return ResponseEntity.ok().body(list.stream().map(UserMapper::toDto).toList());
    }

}
