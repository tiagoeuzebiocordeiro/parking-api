package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.Customer;
import com.tiagocordeiro.parkingapi.jwt.JwtUserDetails;
import com.tiagocordeiro.parkingapi.repository.projection.CustomerProjection;
import com.tiagocordeiro.parkingapi.service.CustomerService;
import com.tiagocordeiro.parkingapi.service.UserService;
import com.tiagocordeiro.parkingapi.web.dto.CustomerCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.CustomerResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.PageableDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.CustomerMapper;
import com.tiagocordeiro.parkingapi.web.dto.mapper.PageableMapper;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new customer", description = "Resource for customer creation with user association. " +
    "Requests needs a bearer token usage. Restrict access for CUSTOMER role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
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

    @Operation(summary = "Find a customer", description = "Resource for find a customer by id " +
            "Requests needs a bearer token usage. Restrict access for ADMIN role.",
            security = @SecurityRequirement(name = "security"),
            responses = {
            @ApiResponse(responseCode = "201", description = "Resource successfully located",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "403", description = "Resource not authorized for CUSTOMER role", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> getById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        return ResponseEntity.ok().body(CustomerMapper.toDto(customer));
    }

    @Operation(summary = "Retrieve customer list",
            description = "Request requires the use of a bearer token. Access restricted to Role='ADMIN' ",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Represents the returned page"
                    ),
                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Represents the total number of elements per page"
                    ),
                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Represents the sorting of the results. Multiple sorting criteria are supported.")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for CLIENT role",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true)
                                                  @PageableDefault(size =5, sort={"name"}) Pageable pageable) {
        Page<CustomerProjection> list = customerService.findAll(pageable);
        return ResponseEntity.ok().body(PageableMapper.toDto(list));
    }

    @Operation(summary = "Restores authenticated customer's data", description =
    "Requests needs a bearer token usage. Restrict access to Role='CUSTOMER'", security =
    @SecurityRequirement(name = "security"), responses = {
            @ApiResponse(responseCode = "200", description = "Restored successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDto.class))),
            @ApiResponse(responseCode = "403", description = "Resource not authorized for 'ADMIN' Role", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @GetMapping("/details")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Customer customer = customerService.getByUserId(userDetails.getId());
        return ResponseEntity.ok(CustomerMapper.toDto(customer));
    }

}
