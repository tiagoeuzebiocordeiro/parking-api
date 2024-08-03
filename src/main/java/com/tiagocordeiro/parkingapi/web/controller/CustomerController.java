package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.jwt.JwtUserDetails;
import com.tiagocordeiro.parkingapi.service.CustomerService;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.CustomerCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.CustomerResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.CustomerMapper;
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

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody @Valid CustomerCreateDto createDto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Customer customer = CustomerMapper.toCustomer(createDto);
        customer.setUser(userService.findById(userDetails.getId()));
        customerService.create(customer);
        return ResponseEntity.status(201).body(CustomerMapper.toDto(customer));
    }

}
