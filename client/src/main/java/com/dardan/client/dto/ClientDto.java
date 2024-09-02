package com.dardan.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {

    private Integer idClient;

    @NotNull
    private String name;

    @NotNull
    private String gender;

    @NotNull
    private Integer age;

    @NotNull
    private String identification;

    @NotNull
    private String address;

    @NotNull
    private String telephone;

    @NotNull
    private String password;

    @NotNull
    private Boolean state;
}
