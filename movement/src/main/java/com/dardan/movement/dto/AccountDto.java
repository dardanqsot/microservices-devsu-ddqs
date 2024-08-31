package com.dardan.movement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {

    private Integer idAccount;

    private Integer idClient;

    private String accountNumber;

    private String accountType;

    private BigDecimal initialBalance;

    private Boolean status;
}
