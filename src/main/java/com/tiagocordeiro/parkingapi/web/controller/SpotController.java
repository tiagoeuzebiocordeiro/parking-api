package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.Spot;
import com.tiagocordeiro.parkingapi.service.SpotService;
import com.tiagocordeiro.parkingapi.web.dto.SpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.SpotResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.SpotMapper;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/spots")
public class SpotController {

    @Autowired
    private SpotService spotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid SpotCreateDto dto) {
        Spot spot = SpotMapper.toSpot(dto);
        spotService.save(spot);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(spot.getCode()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotResponseDto> getByCode(@PathVariable String code) {
        Spot spot = spotService.findByCode(code);
        return ResponseEntity.ok().body(SpotMapper.toDto(spot));
    }

}
