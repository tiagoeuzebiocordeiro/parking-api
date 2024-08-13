package com.tiagocordeiro.parkingapi.web.dto.mapper;

import com.tiagocordeiro.parkingapi.entity.CustomerSpot;
import com.tiagocordeiro.parkingapi.web.dto.ParkingSpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.ParkingSpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotMapper {

    public static CustomerSpot toParkingSpot(ParkingSpotCreateDto dto) {
        return new ModelMapper().map(dto, CustomerSpot.class);
    }

    public static ParkingSpotResponseDto toDto(CustomerSpot obj) {
        return new ModelMapper().map(obj, ParkingSpotResponseDto.class);
    }

}
