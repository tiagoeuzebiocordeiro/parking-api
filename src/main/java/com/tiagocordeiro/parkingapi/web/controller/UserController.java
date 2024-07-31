package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.UserCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.UserPasswordDto;
import com.tiagocordeiro.parkingapi.web.dto.UserResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.UserMapper;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Get an User by Id",security = @SecurityRequirement(name = "security"),
            description = "Request needs a Bearer Token. Restrict Access to ADMIN or CUSTOMER roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource recovered successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "User without permissions to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))})
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR hasRole('CUSTOMER') AND #id == authentication.principal.id") /*don't need to explicit "ROLE_" prefix, spring security already works with it */
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(user));
    }

    @Operation(summary = "Update password of an user",security = @SecurityRequirement(name = "security"),
            description = "Request needs a Bearer Token. Restrict Access to ADMIN or CUSTOMER roles",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password successfully updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password doesn't match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "User without permissions to access this resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid or poorly formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto objDTO) {
        User user = service.updatePassword(id, objDTO.getCurrentPassword(), objDTO.getNewPassword(), objDTO.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List all available users", security = @SecurityRequirement(name = "security"),description = "Resource for get all users", responses = {
            @ApiResponse(responseCode = "200", description = "Request needs a Bearer Token. Restrict Access to ADMIN role",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))),
            @ApiResponse(responseCode = "403", description = "User without permissions to access this resource",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> list = service.getAll();
        return ResponseEntity.ok().body(list.stream().map(UserMapper::toDto).toList());
    }

}
