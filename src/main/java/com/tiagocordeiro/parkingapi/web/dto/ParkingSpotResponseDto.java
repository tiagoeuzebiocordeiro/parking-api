package com.tiagocordeiro.parkingapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingSpotResponseDto {

    private String licensePlate;

    private String brand;

    private String model;

    private String color;

    private String customerCpf; /*Model mapper understand: cpf from Customer object*/

    private String receipt;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime exitDate;

    private String spotCode; /*Model mapper understand: code from Spot object*/

    private BigDecimal value;

    private BigDecimal discount;
}
