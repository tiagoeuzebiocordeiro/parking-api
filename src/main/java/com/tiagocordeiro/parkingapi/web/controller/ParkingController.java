package com.tiagocordeiro.parkingapi.web.controller;

import com.tiagocordeiro.parkingapi.entity.CustomerSpot;
import com.tiagocordeiro.parkingapi.service.ParkingSpotService;
import com.tiagocordeiro.parkingapi.web.dto.ParkingSpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.ParkingSpotResponseDto;
import com.tiagocordeiro.parkingapi.web.dto.mapper.ParkingSpotMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/parking-spots")
public class ParkingController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpotResponseDto> checkIn(@RequestBody @Valid ParkingSpotCreateDto dto) {
        CustomerSpot customerSpot = ParkingSpotMapper.toParkingSpot(dto);
        parkingSpotService.checkIn(customerSpot);

        ParkingSpotResponseDto responseDto = ParkingSpotMapper.toDto(customerSpot);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(customerSpot.getReceipt()).toUri();

        return ResponseEntity.created(uri).body(responseDto);

    }

}
