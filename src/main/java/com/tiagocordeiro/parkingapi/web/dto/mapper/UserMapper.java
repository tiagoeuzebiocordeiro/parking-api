package com.tiagocordeiro.parkingapi.web.dto.mapper;

import com.tiagocordeiro.parkingapi.entity.User;
import com.tiagocordeiro.parkingapi.web.dto.UserCreateDto;
import com.tiagocordeiro.parkingapi.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class UserMapper {

    public static User toUser(UserCreateDto createDto) {
        return new ModelMapper().map(createDto, User.class);
    }

    public static UserResponseDto toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDto> props = new PropertyMap<User, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(user, UserResponseDto.class);
    }

}
