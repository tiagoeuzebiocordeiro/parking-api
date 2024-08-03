package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.jwt.JwtUserDetails;
import com.tiagocordeiro.parkingapi.service.CustomerService;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.CustomerCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.CustomerResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.CustomerMapper;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new customer", description = "Resource for customer creation with user association. " +
    "Requests needs a bearer token usage. Restrict access for CUSTOMER role.", responses = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Customer's cpf already registered in the system.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Not processed resource because of less data information or " +
                    "invalid data information.", content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Resource not authorized for ADMIN role", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody @Valid CustomerCreateDto createDto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Customer customer = CustomerMapper.toCustomer(createDto);
        customer.setUser(userService.findById(userDetails.getId()));
        customerService.create(customer);
        return ResponseEntity.status(201).body(CustomerMapper.toDto(customer));
    }

}
