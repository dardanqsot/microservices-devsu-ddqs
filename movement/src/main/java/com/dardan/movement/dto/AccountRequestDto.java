package com.dardan.movement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequestDto {

    private Integer idAccount;

    @NotNull
    private Integer idClient;

    @NotNull
    private String accountNumber;

    @NotNull
    private Integer idAccountType;

    @NotNull
    private BigDecimal initialBalance;

    private BigDecimal balance;

    @NotNull
    private Boolean status;
}
