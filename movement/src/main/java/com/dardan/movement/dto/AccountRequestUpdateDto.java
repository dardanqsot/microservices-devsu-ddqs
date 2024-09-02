package com.dardan.movement.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountRequestUpdateDto {

    private Integer idAccount;

    private Integer idClient;

    private Integer idAccountType;

    private Boolean status;
}
