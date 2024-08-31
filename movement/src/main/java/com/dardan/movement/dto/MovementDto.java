package com.dardan.movement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto {

    private Integer idMovement;

    private LocalDateTime movementDate;

    private String movementType;

    private BigDecimal value;

    private BigDecimal balance;
}
