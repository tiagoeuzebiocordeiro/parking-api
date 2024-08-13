package com.tiagocordeiro.parkingapi.web.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotResponseDto {

    private String licensePlate;

    private String brand;

    private String model;

    private String color;

    private String customerCpf; /*Model mapper understand: cpf from Customer object*/

    private String receipt;

    private LocalDateTime entryDate;

    private LocalDateTime exitDate;

    private String spotCode; /*Model mapper understand: code from Spot object*/

    private BigDecimal value;

    private BigDecimal discount;
}
