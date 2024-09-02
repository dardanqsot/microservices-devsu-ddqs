package com.dardan.movement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementDto {

    private Integer idMovement;

    @NotNull
    private Integer idAccount;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate movementDate;

    @NotNull
    private Integer idMovementType;

    @NotNull
    private BigDecimal value;

    private BigDecimal balance;
}
