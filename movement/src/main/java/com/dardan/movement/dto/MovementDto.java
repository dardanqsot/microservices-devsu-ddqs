package com.dardan.movement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto {

    private Integer idMovement;

    private Integer idAccount;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate movementDate;

    private String movementType;

    private BigDecimal value;

    private BigDecimal balance;
}
