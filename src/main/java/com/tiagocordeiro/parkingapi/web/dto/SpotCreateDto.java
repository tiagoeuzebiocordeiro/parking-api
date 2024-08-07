package com.tiagocordeiro.parkingapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SpotCreateDto {

    @NotBlank
    @Size(min = 4, max = 4)
    private String code;
    @NotBlank
    @Pattern(regexp = "FREE|OCCUPIED")
    private String status;
}
