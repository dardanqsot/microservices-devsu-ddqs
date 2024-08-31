package com.dardan.movement.proxy.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private Integer idClient;

    private String name;

    private String gender;

    private Integer age;

    private String identification;

    private String address;

    private String telephone;

    private String password;

    private Boolean state;
}
