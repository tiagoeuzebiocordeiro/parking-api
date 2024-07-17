package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.UserCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.UserPasswordDto;
import com.tiagocordeiro.parkingapi.web.dto.UserResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.UserMapper;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Contains all relative resource operations for create, update and read an user.")
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Create a new user",
            description = "Resource for user creation",
            responses = {@ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "E-mail already registered.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource cannot be processed because there is a invalid input data information."
                            , content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto objDto) {
        User user = service.create(UserMapper.toUser(objDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Get an User by Id",
            description = "Resource for recover an user by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource recovered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @Operation(summary = "Update password of an user",
            description = "Resource for update an user password",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password doesn't match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
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
