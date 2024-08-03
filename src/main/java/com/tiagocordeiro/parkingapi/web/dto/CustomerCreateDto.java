package com.tiagocordeiro.parkingapi.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CustomerCreateDto {

    @NotBlank
    @Size(min = 5, max = 100)
    private String name;

    @CPF
    @Size(min = 11, max = 11)
    private String cpf;
}
