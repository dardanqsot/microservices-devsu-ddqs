package com.dardan.client.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique=true)
    private String identification;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

}
