package com.dardan.movement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ReportDto {

    private Integer idMovement;

    private Integer idAccount;

    private Integer idClient;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate fecha;

    private String cliente;

    private String numeroCuenta;

    private String tipo;

    private BigDecimal saldoInicial;

    private boolean estado;

    private BigDecimal movimiento;

    private BigDecimal saldoDisponible;
}
