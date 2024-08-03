package com.tiagocordeiro.parkingapi.web.dto;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String cpf;
}
