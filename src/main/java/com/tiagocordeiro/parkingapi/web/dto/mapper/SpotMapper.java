package com.tiagocordeiro.parkingapi.web.dto.mapper;

import com.tiagocordeiro.parkingapi.entity.Spot;
import com.tiagocordeiro.parkingapi.web.dto.SpotCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.SpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotMapper {

    public static Spot toSpot(SpotCreateDto dto) {
        return new ModelMapper().map(dto, Spot.class);
    }

    public static SpotResponseDto toDto(Spot obj) {
        return new ModelMapper().map(obj, SpotResponseDto.class);
    }

}
