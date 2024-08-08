package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.Spot;
import com.tiagocordeiro.parkingapi.service.SpotService;
import com.tiagocordeiro.parkingapi.web.dto.SpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.SpotResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.SpotMapper;
import com.tiagocordeiro.parkingapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Spots", description = "Contains all resources for Spots")
@RestController
@RequestMapping("api/v1/spots")
public class SpotController {

    @Autowired
    private SpotService spotService;

    @Operation(summary = "Create a new spot", description = "Resource for spot creation. Requests" +
            "need a bearer token usage. Restrict access for 'ADMIN' Role.", responses = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully", headers = @Header(name =
                    HttpHeaders.LOCATION, description = "Resource's Creation URL")),
            @ApiResponse(responseCode = "409", description = "Spot already registered", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Resource cannot be processed. Reason: Missing data or invalid data.", content =
                @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid SpotCreateDto dto) {
        Spot spot = SpotMapper.toSpot(dto);
        spotService.save(spot);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(spot.getCode()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Find a Parking Spot", description = "Resource for find a parking spot by spot code " +
            "Request needs a bearer token usage. Restrict access for 'ADMIN' role",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource created successfully",
                            content = @Content(mediaType = " application/json",
                                    schema = @Schema(implementation = SpotResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Parking Spot not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Resource not allowed for CUSTOMER role",
                            content = @Content(mediaType = " application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    )
            })
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotResponseDto> getByCode(@PathVariable String code) {
        Spot spot = spotService.findByCode(code);
        return ResponseEntity.ok().body(SpotMapper.toDto(spot));
    }

}
